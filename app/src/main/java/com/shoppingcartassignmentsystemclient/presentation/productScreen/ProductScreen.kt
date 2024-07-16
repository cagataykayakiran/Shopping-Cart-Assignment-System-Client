package com.shoppingcartassignmentsystemclient.presentation.productScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shoppingcartassignmentsystemclient.domain.model.Product
import com.shoppingcartassignmentsystemclient.presentation.MainViewModel
import com.shoppingcartassignmentsystemclient.presentation.productScreen.components.AddProductDialogContent
import com.shoppingcartassignmentsystemclient.presentation.productScreen.components.AppBottomBar
import com.shoppingcartassignmentsystemclient.presentation.productScreen.components.BadgeInteractive
import com.shoppingcartassignmentsystemclient.presentation.productScreen.components.ProductListItem
import com.shoppingcartassignmentsystemclient.presentation.ui.theme.backgroundColor
import com.shoppingcartassignmentsystemclient.presentation.ui.theme.primaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    navController: NavController,
    showActions: Boolean = false
) {
    var showDialog by remember { mutableStateOf(false) }
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var errorProductName by remember { mutableStateOf("") }
    var errorProductPrice by remember { mutableStateOf("") }
    var selectedProductId by remember { mutableLongStateOf(0L) }
    var searchQuery by remember { mutableStateOf("") }

    val products by mainViewModel.products.collectAsState()
    val cartProducts by mainViewModel.cartProducts.collectAsState()

    Log.d("cartProducts", cartProducts.productsWithQuantity.size.toString())

    val focusManager = LocalFocusManager.current

    Scaffold(
        containerColor = backgroundColor,
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    if (showActions) Text(
                        text = "Product Modify",
                        color = Color.White
                    ) else Text(text = "Products", color = Color.White)
                },
                actions = {
                    if (!showActions)
                        BadgeInteractive(
                            dataState = cartProducts.productsWithQuantity.size,
                            navController = navController
                        )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryColor
                )
            )
        },
        bottomBar = {
            AppBottomBar(navController = navController)
        },
        floatingActionButton = {
            if (showActions) {
                FloatingActionButton(
                    onClick = { showDialog = true },
                    containerColor = primaryColor,
                    contentColor = Color.White
                ) {
                    Text(text = "+")
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        mainViewModel.onEvent(UIEvent.SearchProduct(it))
                    },
                    singleLine = true,
                    label = { Text("Search Product") },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(products.products) { product ->
                        ProductListItem(
                            product = product,
                            onDeleteClicked = {
                                mainViewModel.onEvent(UIEvent.DeleteProduct(product))
                            },
                            onUpdateClicked = { updatedProduct ->
                                mainViewModel.onEvent(UIEvent.UpdateProduct(updatedProduct))
                            },
                            onAddToCartClicked = {
                                mainViewModel.onEvent(UIEvent.AddProductToCard(product))
                            },
                            quantity = cartProducts.productsWithQuantity[product] ?: 0,
                            onUpdatedToCartClicked = {
                                mainViewModel.onEvent(UIEvent.UpdateProductQuantity(product))
                            },
                            showActions = showActions
                        )
                    }
                }
            }
            if (products.isLoading) {
                CircularProgressIndicator()
            }
            if (products.products.isEmpty()) {
                Text(text = "No products found", textAlign = TextAlign.Center)
            }
        }

        if (showDialog) {
            AlertDialog(
                containerColor = primaryColor,
                onDismissRequest = { showDialog = false },
                title = {
                    Text(
                        text = if (selectedProductId == 0L) "Add New Product" else "Update Product",
                        color = backgroundColor
                    )
                },
                text = {
                    AddProductDialogContent(
                        productName = productName,
                        productPrice = productPrice,
                        onProductNameChange = {
                            productName = it
                            errorProductName = if (it.length in 2..19) {
                                ""
                            } else {
                                "Product name must be between 2 and 20 characters."
                            }
                        },
                        onProductPriceChange = {
                            productPrice = it
                            val price = it.toDoubleOrNull()
                            errorProductPrice = when {
                                price == null -> "Invalid price format."
                                price < 0.01 || price > 99.99 -> "Price must be between 0.01 and 99.99."
                                else -> ""
                            }
                        },
                        errorProductName = errorProductName,
                        errorProductPrice = errorProductPrice
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            mainViewModel.onEvent(
                                UIEvent.SaveProduct(
                                    Product(
                                        name = productName,
                                        price = productPrice.toDouble()
                                    )
                                )
                            )
                            productName = ""
                            productPrice = ""
                            selectedProductId = 0L
                            showDialog = false
                        },
                        enabled = errorProductName.isEmpty() && errorProductPrice.isEmpty()
                    ) {
                        Text(
                            text = if (selectedProductId == 0L) "Add" else "Update",
                            color = backgroundColor
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text(text = "Cancel", color = backgroundColor)
                    }
                }
            )
        }
    }
}

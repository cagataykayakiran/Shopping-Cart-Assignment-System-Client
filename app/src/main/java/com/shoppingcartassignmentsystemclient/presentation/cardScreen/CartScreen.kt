package com.shoppingcartassignmentsystemclient.presentation.cardScreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shoppingcartassignmentsystemclient.presentation.cardScreen.components.CartListItem
import com.shoppingcartassignmentsystemclient.presentation.cardScreen.components.CartScreenTopBar
import com.shoppingcartassignmentsystemclient.presentation.MainViewModel
import com.shoppingcartassignmentsystemclient.data.remote.dto.CartRequest
import com.shoppingcartassignmentsystemclient.presentation.cardScreen.components.CartScreenBottomBar
import com.shoppingcartassignmentsystemclient.data.remote.dto.ProductRequest
import com.shoppingcartassignmentsystemclient.presentation.productScreen.UIEvent
import com.shoppingcartassignmentsystemclient.presentation.ui.theme.backgroundColor

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    navController: NavController
) {
    val cardProducts by mainViewModel.cartProducts.collectAsState()
    val totalPrice by mainViewModel.totalPrice.collectAsState()
    val cardLimitState by mainViewModel.cardLimitState.collectAsState()

    Log.d("cart screen size", cardProducts.productsWithQuantity.size.toString())

    var serverIp by remember { mutableStateOf("") }
    var serverPort by remember { mutableStateOf("") }
    var isPortDialogOpen by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            CartScreenTopBar(
                onClearAllClicked = { mainViewModel.onEvent(UIEvent.DeleteAllProductsFromCard) },
                onNavigateBack = { navController.popBackStack() })
        },
        bottomBar = {
            CartScreenBottomBar(
                cardLimitState = cardLimitState.cartLimit.toString(),
                totalPrice = totalPrice.toString(),
                isPortDialogOpen = isPortDialogOpen,
                onSendClicked = {
                    isPortDialogOpen = true
                },
                onPortDialogDismiss = { isPortDialogOpen = false },
                onPortDialogConfirm = { ip, port ->
                    serverIp = ip
                    serverPort = port
                    isPortDialogOpen = false

                    if (serverIp.isNotEmpty() && serverPort.isNotEmpty()) {
                        val productsList = mutableListOf<ProductRequest>()

                        for ((product, quantity) in cardProducts.productsWithQuantity) {
                            val productRequest = ProductRequest(
                                id = product.id,
                                price = product.price * quantity
                            )
                            productsList.add(productRequest)
                        }

                        val cartData = CartRequest(
                            cartLimit = cardLimitState.cartLimit,
                            products = productsList
                        )

                        mainViewModel.onEvent(
                            UIEvent.SendCartDataToServer(
                                cartData = cartData,
                                serverIp = serverIp,
                                serverPort = serverPort
                            )
                        )
                        mainViewModel.onEvent(
                            UIEvent.ShowToast(
                                message = "Cart sent to server"
                            )
                        )
                    } else {
                        mainViewModel.onEvent(
                            UIEvent.ShowToast(
                                message = "Please enter server IP and port"
                            )
                        )
                    }
                },
                updateCardLimit = {
                    mainViewModel.onEvent(UIEvent.UpdateCardLimit(it))
                }
            )
        }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
        ) {
            if (cardProducts.productsWithQuantity.isNotEmpty()) {
                LazyColumn {
                    items(cardProducts.productsWithQuantity.toList()) { (product, quantity) ->
                        CartListItem(
                            product = product,
                            quantity = quantity,
                            onDeleteClicked = {
                                mainViewModel.onEvent(UIEvent.DeleteProductFromCard(product))
                            },
                            onAddClicked = {
                                mainViewModel.onEvent(UIEvent.AddProductToCard(product))
                            }
                        )
                    }
                }
            } else {
                Text(
                    text = "No products in the cart",
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                        .padding(vertical = 16.dp)
                )
            }
        }
    }
}



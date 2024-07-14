package com.shoppingcartassignmentsystemclient.presentation.productScreen.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.shoppingcartassignmentsystemclient.domain.model.Product

@Composable
fun ProductListItem(
    product: Product,
    onDeleteClicked: () -> Unit,
    onUpdateClicked: (Product) -> Unit,
    onAddToCartClicked: (Product) -> Unit,
    quantity: Int
) {
    var showDialog by remember { mutableStateOf(false) }
    var updatedProductName by remember { mutableStateOf(product.name) }
    var updatedProductPrice by remember { mutableStateOf(product.price.toString()) }
    var errorUpdatedProductName by remember { mutableStateOf("") }
    var errorUpdatedProductPrice by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(13.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(Color.LightGray)
            .clickable {
                showDialog = true
                Log.d("product clicked", product.id.toString())
            }
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "ID:")
                    Text(text = product.id.toString())
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Name:")
                    Text(text = product.name)
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Price:")
                    Text(text = product.price.toString())
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = { onDeleteClicked() },
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
                IconButton(
                    onClick = { onAddToCartClicked(
                        Product(
                        id = product.id,
                        name = product.name,
                        price = product.price
                    )
                    )
                        Log.d("add to cart", product.name) },
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add to cart"
                    )
                }
                Text(text = "Quantity: $quantity")
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Update Product") },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextField(
                        value = updatedProductName,
                        onValueChange = {
                            updatedProductName = it
                            errorUpdatedProductName = if (it.length in 2..19) {
                                ""
                            } else {
                                "Product name must be between 2 and 20 characters."
                            }
                        },
                        isError = errorUpdatedProductName.isNotEmpty(),
                        label = { Text(text = "Updated product name:") },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    if (errorUpdatedProductName.isNotEmpty()) {
                        Text(text = errorUpdatedProductName, color = Color.Red)
                    }
                    TextField(
                        value = updatedProductPrice,
                        onValueChange = {
                            updatedProductPrice = it
                            val price = it.toDoubleOrNull()
                            errorUpdatedProductPrice = when {
                                price == null -> "Invalid price format."
                                price < 0.01 || price > 99.99 -> "Price must be between 0.01 and 99.99."
                                else -> ""
                            }
                        },
                        isError = errorUpdatedProductPrice.isNotEmpty(),
                        label = { Text(text = "Updated product price:") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                    )
                    if (errorUpdatedProductPrice.isNotEmpty()) {
                        Text(text = errorUpdatedProductPrice, color = Color.Red)
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (errorUpdatedProductName.isEmpty() && errorUpdatedProductPrice.isEmpty()) {
                            onUpdateClicked(
                                Product(
                                    id = product.id,
                                    name = updatedProductName,
                                    price = updatedProductPrice.toDouble()
                                )
                            )
                            showDialog = false
                        }
                    },
                    enabled = errorUpdatedProductName.isEmpty() && errorUpdatedProductPrice.isEmpty()
                ) {
                    Text(text = "Update")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}
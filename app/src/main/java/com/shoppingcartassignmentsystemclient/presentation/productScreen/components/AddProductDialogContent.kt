package com.shoppingcartassignmentsystemclient.presentation.productScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AddProductDialogContent(
    productName: String,
    productPrice: String,
    onProductNameChange: (String) -> Unit,
    onProductPriceChange: (String) -> Unit,
    errorProductName: String,
    errorProductPrice: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = productName,
            onValueChange = onProductNameChange,
            isError = errorProductName.isNotEmpty(),
            label = { Text(text = "Product name:") },
            modifier = Modifier.fillMaxWidth(),
        )
        if (errorProductName.isNotEmpty()) {
            Text(text = errorProductName, color = Color.Red)
        }
        TextField(
            value = productPrice,
            onValueChange = onProductPriceChange,
            isError = errorProductPrice.isNotEmpty(),
            label = { Text(text = "Product price:") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
        )
        if (errorProductPrice.isNotEmpty()) {
            Text(text = errorProductPrice, color = Color.Red)
        }
    }
}
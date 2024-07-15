package com.shoppingcartassignmentsystemclient.presentation.cardScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shoppingcartassignmentsystemclient.domain.model.Product

@Composable
fun CartListItem(
    modifier: Modifier = Modifier,
    product: Product,
    quantity: Int,
    onDeleteClicked: () -> Unit,
    onAddClicked: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(13.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onDeleteClicked) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "a")
                }
                Box(
                    modifier = Modifier.size(25.dp, 25.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = quantity.toString())
                }
                IconButton(onClick = onAddClicked) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "a")
                }
            }

        }
    }
}
package com.shoppingcartassignmentsystemclient.presentation.cardScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.shoppingcartassignmentsystemclient.presentation.ui.theme.backgroundColor
import com.shoppingcartassignmentsystemclient.presentation.ui.theme.primaryColor

@Composable
fun CartScreenBottomBar(
    modifier: Modifier = Modifier,
    cardLimitState: String,
    totalPrice: String,
    isPortDialogOpen: Boolean,
    onSendClicked: () -> Unit,
    onPortDialogDismiss: () -> Unit,
    onPortDialogConfirm: (String, String) -> Unit,
    updateCardLimit: (Double) -> Unit
) {

    var isEditDialogOpen by remember { mutableStateOf(false) }
    var updatedCardLimit by remember { mutableStateOf(cardLimitState) }

    BottomAppBar(
        containerColor = backgroundColor,
        content = {
            Row(
                modifier = modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Total: $$totalPrice",
                        modifier = Modifier
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically

                    ){
                        Text(
                            text = "Card Limit: $$cardLimitState",
                            modifier = Modifier
                        )
                        IconButton(onClick = { isEditDialogOpen = true }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }

                }
                Button(
                    onClick = onSendClicked,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor
                    )
                ) {
                    Text(text = "Send")
                }
                if (isPortDialogOpen) {
                    PortDialog(
                        onConfirm = onPortDialogConfirm,
                        onDismiss = onPortDialogDismiss
                    )
                }
            }
        }
    )
    if (isEditDialogOpen) {
        AlertDialog(
            onDismissRequest = { isEditDialogOpen = false },
            title = { Text("Edit Card Limit") },
            text = {
                TextField(
                    value = updatedCardLimit,
                    onValueChange = { updatedCardLimit = it },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            },
            confirmButton = {
                Button(
                    onClick = { updateCardLimit(updatedCardLimit.toDouble())
                        isEditDialogOpen = false
                    },
                ) {
                    Text("Update")
                }
            },
            dismissButton = {
                Button(
                    onClick = { isEditDialogOpen = false },
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
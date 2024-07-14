package com.shoppingcartassignmentsystemclient.presentation.cardScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun CardScreenBottomBar(
    modifier: Modifier = Modifier,
    cardLimitState: String,
    totalPrice: String,
    isPortDialogOpen: Boolean,
    onSendClicked: () -> Unit,
    onPortDialogDismiss: () -> Unit,
    onPortDialogConfirm: (String, String) -> Unit
) {
    BottomAppBar(
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
                        text = "Card Limit: $$cardLimitState",
                        modifier = Modifier
                    )
                    Text(
                        text = "Total: $$totalPrice",
                        modifier = Modifier
                    )
                }
                Button(
                    onClick = onSendClicked,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
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
}
package com.shoppingcartassignmentsystemclient.presentation.cardScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.unit.dp
import com.shoppingcartassignmentsystemclient.presentation.ui.theme.backgroundColor
import com.shoppingcartassignmentsystemclient.presentation.ui.theme.primaryColor

@Composable
fun PortDialog(
    onConfirm: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var ip by remember { mutableStateOf("192.168.1.180") }
    var port by remember { mutableStateOf("7575") }

    AlertDialog(
        containerColor = primaryColor,
        onDismissRequest = onDismiss,
        title = { Text("Server Connection") },
        text = {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = ip,
                    onValueChange = { ip = it },
                    label = { Text("Server IP") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = port,
                    onValueChange = { port = it },
                    label = { Text("Port") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(ip, port)
                }
            ) {
                Text("Connect", color = backgroundColor)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel", color = backgroundColor)
            }
        }
    )
}
package com.shoppingcartassignmentsystemclient.presentation.productScreen.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shoppingcartassignmentsystemclient.util.Screen

@Composable
fun BadgeInteractive(dataState: Int, navController: NavController) {
    BadgedBox(
        badge = {
            Badge(
                containerColor = Color.White,
                contentColor = if (dataState == 0) Color.Black else Color.Red
            ) {
                Text(text = "$dataState", fontSize = 15.sp)
            }
        }
    ) {
        IconButton(onClick = { navController.navigate(Screen.CardScreen.route) }) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Shopping Cart"
            )
        }
    }
}
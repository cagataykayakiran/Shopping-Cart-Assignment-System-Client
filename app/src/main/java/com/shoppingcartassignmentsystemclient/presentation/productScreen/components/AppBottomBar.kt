package com.shoppingcartassignmentsystemclient.presentation.productScreen.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.shoppingcartassignmentsystemclient.presentation.ui.theme.secondaryColor
import com.shoppingcartassignmentsystemclient.presentation.ui.theme.primaryColor
import com.shoppingcartassignmentsystemclient.util.Screen

data class BottomNavigationItem(
    val title: String,
    val route: String
)



@Composable
fun AppBottomBar(navController: NavController) {
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            route = Screen.MainScreen.route
        ),
        BottomNavigationItem(
            title = "Products Modify",
            route = Screen.ProductModifyScreen.route
        )
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = primaryColor
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = secondaryColor
                ),
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    when (index) {
                        0 -> Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = item.title,
                            tint = Color.White
                        )
                        1 -> Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = item.title,
                            tint = Color.White
                        )
                    }
                },
                label = { Text(text = item.title, color = Color.White) }
            )
        }
    }
}
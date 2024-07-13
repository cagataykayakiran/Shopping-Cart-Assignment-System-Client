package com.shoppingcartassignmentsystemclient.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shoppingcartassignmentsystemclient.util.Screen

@Composable
fun NavigationGraph(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.AddProductScreen.route
    ) {
        composable(Screen.AddProductScreen.route) {

        }
    }
}
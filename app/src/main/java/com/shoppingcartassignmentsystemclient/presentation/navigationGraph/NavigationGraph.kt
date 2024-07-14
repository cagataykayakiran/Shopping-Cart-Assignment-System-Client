package com.shoppingcartassignmentsystemclient.presentation.navigationGraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shoppingcartassignmentsystemclient.presentation.cardScreen.CardScreen
import com.shoppingcartassignmentsystemclient.presentation.productScreen.ProductScreen
import com.shoppingcartassignmentsystemclient.presentation.MainViewModel
import com.shoppingcartassignmentsystemclient.util.Screen

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel()
) {

    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.AddProductScreen.route
    ) {
        composable(Screen.AddProductScreen.route) {
            ProductScreen(
                navController = navController,
                mainViewModel = mainViewModel,
            )
        }
        composable(Screen.CardScreen.route) {
            CardScreen(mainViewModel = mainViewModel, navController = navController)
        }
    }
}
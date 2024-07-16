package com.shoppingcartassignmentsystemclient.util

sealed class Screen(val route: String) {
    data object MainScreen: Screen("main_screen")
    data object CardScreen: Screen("card_screen")
    data object ProductModifyScreen: Screen("products_modify_screen")
}
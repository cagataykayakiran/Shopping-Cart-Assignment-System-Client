package com.shoppingcartassignmentsystemclient.util

sealed class Screen(val route: String) {
    data object AddProductScreen: Screen("add_product_screen")
    data object CardScreen: Screen("card_screen")
}
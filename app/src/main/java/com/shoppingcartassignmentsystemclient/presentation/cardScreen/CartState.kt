package com.shoppingcartassignmentsystemclient.presentation.cardScreen

import com.shoppingcartassignmentsystemclient.domain.model.Product

data class CartState(
    val productsWithQuantity: MutableMap<Product, Int> = mutableMapOf(),
)

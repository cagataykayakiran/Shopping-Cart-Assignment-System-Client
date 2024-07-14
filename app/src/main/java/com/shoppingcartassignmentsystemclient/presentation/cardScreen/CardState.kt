package com.shoppingcartassignmentsystemclient.presentation.cardScreen

import com.shoppingcartassignmentsystemclient.domain.model.Product

data class CardState(
    val productsWithQuantity: MutableMap<Product, Int> = mutableMapOf(),
    val cardLimit: Double = 899.89
)

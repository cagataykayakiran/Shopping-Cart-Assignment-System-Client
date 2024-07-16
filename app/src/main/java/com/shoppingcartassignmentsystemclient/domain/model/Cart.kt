package com.shoppingcartassignmentsystemclient.domain.model

data class Cart(
    val cartDateTime: String,
    val productId: Long,
    val price: Double,
    val id: Long = 0L
)

package com.shoppingcartassignmentsystemclient.data.remote.dto

data class CartRequest(
    val cartLimit: Double,
    val products: List<ProductRequest>
)
package com.shoppingcartassignmentsystemclient.presentation.productScreen

import com.shoppingcartassignmentsystemclient.domain.model.Product

data class ProductState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

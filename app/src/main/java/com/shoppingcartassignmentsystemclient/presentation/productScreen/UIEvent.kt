package com.shoppingcartassignmentsystemclient.presentation.productScreen

import com.shoppingcartassignmentsystemclient.domain.model.Product

sealed class UIEvent {
    data class DeleteProduct(val product: Product) : UIEvent()
    data class UpdateProduct(val product: Product) : UIEvent()
    data class SaveProduct(val product: Product) : UIEvent()
    data class SearchProduct(val query: String) : UIEvent()
    data class AddProductToCard(val product: Product) : UIEvent()
    data class DeleteProductFromCard(val product: Product) : UIEvent()
    data object DeleteAllProductsFromCard : UIEvent()
    data class UpdateCardLimit(val limit: Double) : UIEvent()
}
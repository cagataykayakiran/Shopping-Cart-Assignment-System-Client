package com.shoppingcartassignmentsystemclient.domain.repository

import com.shoppingcartassignmentsystemclient.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getProductById(id: Long): Product
    suspend fun insertProduct(product: Product)
    fun getProducts(): Flow<List<Product>>
    suspend fun deleteProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun searchProducts(query: String): List<Product>
}
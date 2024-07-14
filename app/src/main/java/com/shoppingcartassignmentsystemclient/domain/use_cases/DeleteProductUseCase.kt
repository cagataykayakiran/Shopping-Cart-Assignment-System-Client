package com.shoppingcartassignmentsystemclient.domain.use_cases

import android.util.Log
import com.shoppingcartassignmentsystemclient.domain.model.Product
import com.shoppingcartassignmentsystemclient.domain.repository.ProductRepository
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    suspend operator fun invoke(product: Product) {
        Log.d("product", product.toString())
        return productRepository.deleteProduct(product)
    }
}
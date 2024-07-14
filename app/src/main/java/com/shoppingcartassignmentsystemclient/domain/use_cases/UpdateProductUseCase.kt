package com.shoppingcartassignmentsystemclient.domain.use_cases

import com.shoppingcartassignmentsystemclient.domain.model.Product
import com.shoppingcartassignmentsystemclient.domain.repository.ProductRepository
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    suspend operator fun invoke(product: Product) {
        productRepository.updateProduct(product)
    }
}
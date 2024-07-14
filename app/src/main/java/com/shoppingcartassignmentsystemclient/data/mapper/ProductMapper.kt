package com.shoppingcartassignmentsystemclient.data.mapper

import com.shoppingcartassignmentsystemclient.data.local.entity.ProductEntity
import com.shoppingcartassignmentsystemclient.domain.model.Product

object ProductMapper{

    fun mapProductToProductEntity(product: Product): ProductEntity {
        return ProductEntity(
            id = product.id,
            name = product.name,
            price = product.price
        )
    }

    fun mapProductEntityToProduct(productEntity: ProductEntity): Product {
        return Product(
            id = productEntity.id,
            name = productEntity.name,
            price = productEntity.price
        )
    }
}


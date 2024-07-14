package com.shoppingcartassignmentsystemclient.data.repository

import android.util.Log
import com.shoppingcartassignmentsystemclient.data.local.dao.ProductDao
import com.shoppingcartassignmentsystemclient.data.mapper.ProductMapper
import com.shoppingcartassignmentsystemclient.domain.model.Product
import com.shoppingcartassignmentsystemclient.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDao: ProductDao
): ProductRepository {

    override suspend fun getProductById(id: Long): Product {
        return ProductMapper.mapProductEntityToProduct(productDao.getProductById(id))
    }

    override suspend fun insertProduct(product: Product) {
        return productDao.insertProduct(ProductMapper.mapProductToProductEntity(product))
    }

    override fun getProducts(): Flow<List<Product>> {
        return productDao.getProducts().map { it.map { ProductMapper.mapProductEntityToProduct(it) }}
    }

    override suspend fun deleteProduct(product: Product) {
        Log.d("product", product.toString())
        return productDao.deleteProduct(ProductMapper.mapProductToProductEntity(product))
    }

    override suspend fun updateProduct(product: Product) {
        return productDao.updateProduct(ProductMapper.mapProductToProductEntity(product))
    }

    override suspend fun searchProducts(query: String): List<Product> {
        return productDao.searchProducts(query).map { ProductMapper.mapProductEntityToProduct(it) }
    }

}
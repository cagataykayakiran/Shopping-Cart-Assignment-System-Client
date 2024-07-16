package com.shoppingcartassignmentsystemclient.data.repository

import com.shoppingcartassignmentsystemclient.data.local.dao.CartDao
import com.shoppingcartassignmentsystemclient.data.mapper.CartMapper
import com.shoppingcartassignmentsystemclient.data.remote.dto.CartRequest
import com.shoppingcartassignmentsystemclient.data.remote.dto.CartResponse
import com.shoppingcartassignmentsystemclient.di.RetrofitInstance
import com.shoppingcartassignmentsystemclient.domain.model.Cart
import com.shoppingcartassignmentsystemclient.domain.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
): CartRepository {

    override suspend fun saveCart(cart: Cart) {
        return cartDao.insertCart(CartMapper.mapCartToCartEntity(cart))
    }

    override suspend fun sendCartDataToServer(
        cartData: CartRequest,
        serverIp: String,
        serverPort: String
    ): CartResponse {
        val baseUrl = "http://$serverIp:$serverPort"
        val apiService = RetrofitInstance.getRetrofitInstance(baseUrl)
        return apiService.sendCart(cartData)
    }
}
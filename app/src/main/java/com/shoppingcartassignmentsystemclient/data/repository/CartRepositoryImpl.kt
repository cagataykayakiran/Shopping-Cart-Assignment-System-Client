package com.shoppingcartassignmentsystemclient.data.repository

import com.shoppingcartassignmentsystemclient.data.local.dao.CartDao
import com.shoppingcartassignmentsystemclient.data.local.entity.CartEntity
import com.shoppingcartassignmentsystemclient.data.remote.ApiCall
import com.shoppingcartassignmentsystemclient.data.remote.dto.CartRequest
import com.shoppingcartassignmentsystemclient.data.remote.dto.CartResponse
import com.shoppingcartassignmentsystemclient.di.RetrofitInstance
import com.shoppingcartassignmentsystemclient.domain.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
): CartRepository {

    override suspend fun saveCart(cart: CartEntity) {
        return cartDao.insertCart(cart)
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
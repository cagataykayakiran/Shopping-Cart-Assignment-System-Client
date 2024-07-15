package com.shoppingcartassignmentsystemclient.domain.repository

import com.shoppingcartassignmentsystemclient.data.local.entity.CartEntity
import com.shoppingcartassignmentsystemclient.data.remote.dto.CartRequest
import com.shoppingcartassignmentsystemclient.data.remote.dto.CartResponse

interface CartRepository {

    suspend fun saveCart(cart: CartEntity)

    suspend fun sendCartDataToServer(cartData: CartRequest, serverIp: String, serverPort: String): CartResponse

}
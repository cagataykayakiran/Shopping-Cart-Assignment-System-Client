package com.shoppingcartassignmentsystemclient.domain.repository

import com.shoppingcartassignmentsystemclient.data.remote.dto.CartRequest
import com.shoppingcartassignmentsystemclient.data.remote.dto.CartResponse
import com.shoppingcartassignmentsystemclient.domain.model.Cart

interface CartRepository {

    suspend fun saveCart(cart: Cart)

    suspend fun sendCartDataToServer(cartData: CartRequest, serverIp: String, serverPort: String): CartResponse

}
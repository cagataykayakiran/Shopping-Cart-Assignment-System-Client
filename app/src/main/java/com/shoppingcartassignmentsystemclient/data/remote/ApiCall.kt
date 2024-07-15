package com.shoppingcartassignmentsystemclient.data.remote

import com.shoppingcartassignmentsystemclient.data.remote.dto.CartResponse
import com.shoppingcartassignmentsystemclient.data.remote.dto.CartRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiCall {

    @POST("/cart")
    suspend fun sendCart(@Body cartRequest: CartRequest): CartResponse
}
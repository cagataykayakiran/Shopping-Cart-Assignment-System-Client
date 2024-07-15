package com.shoppingcartassignmentsystemclient.domain.use_cases

import android.util.Log
import com.shoppingcartassignmentsystemclient.data.remote.dto.CartRequest
import com.shoppingcartassignmentsystemclient.data.remote.dto.CartResponse
import com.shoppingcartassignmentsystemclient.domain.repository.CartRepository
import com.shoppingcartassignmentsystemclient.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SendCartToServerUseCase @Inject constructor(
    private val repository: CartRepository
) {
    operator fun invoke(
        cartRequest: CartRequest,
        serverIp: String,
        serverPort: String
    ): Flow<Resource<CartResponse>> = flow {
        Log.d("cart request", cartRequest.toString())
        try {
            emit(Resource.Loading())
            val response = repository.sendCartDataToServer(cartRequest, serverIp, serverPort)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred"))
        }
    }
}
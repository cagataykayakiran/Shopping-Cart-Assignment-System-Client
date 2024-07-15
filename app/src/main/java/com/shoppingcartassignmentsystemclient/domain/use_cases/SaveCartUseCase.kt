package com.shoppingcartassignmentsystemclient.domain.use_cases

import com.shoppingcartassignmentsystemclient.data.local.entity.CartEntity
import com.shoppingcartassignmentsystemclient.domain.repository.CartRepository
import javax.inject.Inject

class SaveCartUseCase @Inject constructor(
    private val repository: CartRepository
) {

    suspend operator fun invoke(cart: CartEntity) {
        repository.saveCart(cart)
    }

}
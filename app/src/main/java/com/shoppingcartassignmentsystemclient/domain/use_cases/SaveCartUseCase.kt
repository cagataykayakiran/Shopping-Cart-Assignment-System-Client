package com.shoppingcartassignmentsystemclient.domain.use_cases

import com.shoppingcartassignmentsystemclient.domain.model.Cart
import com.shoppingcartassignmentsystemclient.domain.repository.CartRepository
import javax.inject.Inject

class SaveCartUseCase @Inject constructor(
    private val repository: CartRepository
) {

    suspend operator fun invoke(cart: Cart) {
        repository.saveCart(cart)
    }

}
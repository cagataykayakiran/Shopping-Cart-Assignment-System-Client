package com.shoppingcartassignmentsystemclient.data.mapper

import com.shoppingcartassignmentsystemclient.data.local.entity.CartEntity
import com.shoppingcartassignmentsystemclient.domain.model.Cart

object CartMapper {

    fun mapCartEntityToCart(cartEntity: CartEntity): Cart {
        return Cart(
            id = cartEntity.id,
            cartDateTime = cartEntity.cartDateTime,
            productId = cartEntity.productId,
            price = cartEntity.price
        )
    }

    fun mapCartToCartEntity(cart: Cart): CartEntity {
        return CartEntity(
            id = cart.id,
            cartDateTime = cart.cartDateTime,
            productId = cart.productId,
            price = cart.price
        )
    }
}
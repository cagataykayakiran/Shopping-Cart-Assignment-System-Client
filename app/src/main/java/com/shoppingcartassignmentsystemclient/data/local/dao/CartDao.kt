package com.shoppingcartassignmentsystemclient.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.shoppingcartassignmentsystemclient.data.local.entity.CartEntity

@Dao
interface CartDao {

    @Insert
    suspend fun insertCart(cart: CartEntity)

    @Query("SELECT * FROM carts WHERE cartDateTime = :cartDateTime")
    suspend fun getCartsByDateTime(cartDateTime: String): List<CartEntity>

}
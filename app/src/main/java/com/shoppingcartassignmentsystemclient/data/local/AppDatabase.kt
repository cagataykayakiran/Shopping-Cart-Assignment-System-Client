package com.shoppingcartassignmentsystemclient.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shoppingcartassignmentsystemclient.data.local.dao.CartDao
import com.shoppingcartassignmentsystemclient.data.local.dao.ProductDao
import com.shoppingcartassignmentsystemclient.data.local.entity.CartEntity
import com.shoppingcartassignmentsystemclient.data.local.entity.ProductEntity


@Database(entities = [ProductEntity::class, CartEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
}
package com.shoppingcartassignmentsystemclient.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "carts",
    foreignKeys = [ForeignKey(
        entity = ProductEntity::class,
        parentColumns = ["id"],
        childColumns = ["productId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CartEntity(
    val cartDateTime: String,
    val productId: Long,
    val price: Double,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)

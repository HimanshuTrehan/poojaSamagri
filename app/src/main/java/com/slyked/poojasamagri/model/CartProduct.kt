package com.slyked.admin.product.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CartTable")
data class CartProduct(
    @PrimaryKey val id: Int,
    val variant: ProductsVariant?,
    val description: String?,
    val image: String?,
    val name: String?,
    val quantity: Int?,
)
package com.slyked.admin.product.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CartTable")
data class CartProduct(
    @PrimaryKey(autoGenerate = true) val id: Int?=null,
    val productId:Int,
    val variant: ProductsVariant?,
    val description: String?,
    val image: String?,
    val name: String?,
    val quantity: Int?,
)
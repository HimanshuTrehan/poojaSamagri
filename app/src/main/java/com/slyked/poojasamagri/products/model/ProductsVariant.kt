package com.slyked.admin.product.model

import androidx.room.Entity

@Entity(tableName = "ProductVariants")

data class ProductsVariant(
    val createdAt: String,
    val id: Int,
    val price: String,
    val product_id: Int,
    val qty: String,
    val updatedAt: String
)
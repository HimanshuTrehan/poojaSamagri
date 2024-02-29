package com.slyked.admin.product.model

import androidx.room.Entity

@Entity(tableName = "ProductVariants")

data class ProductsVariant(
    val createdAt: String?=null,
    val id: Int?=null,
    val price: String,
    val mrp: String,
    val product_id: Int,
    val qty: String,
    val updatedAt: String?=null
)
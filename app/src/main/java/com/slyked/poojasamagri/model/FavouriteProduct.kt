package com.slyked.admin.product.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavouriteTable")
data class FavouriteProduct(
    @PrimaryKey val id: Int,
    val ProductsVariants: List<ProductsVariant>,
    val description: String?,
    val image: String?,
    val name: String?,
    val out_of_stock: Boolean?,
)
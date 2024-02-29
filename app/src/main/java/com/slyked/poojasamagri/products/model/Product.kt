package com.slyked.admin.product.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.slyked.poojasamagri.products.model.ProductCategories

data class Product(
    @PrimaryKey val id: Int,
    val Category: Category?,
    val ProductsVariants: List<ProductsVariant>?,
    val SubCategory: SubCategory?,
    val category_id: Int?,
    val ProductCategories: List<ProductCategories?>,
    val createdAt: String,
    val description: String?,
    val image: String?,
    val name: String?,
    val out_of_stock: Boolean?,
    val price: String?,
    val sub_category_id: Int?,
    val updatedAt: String
)
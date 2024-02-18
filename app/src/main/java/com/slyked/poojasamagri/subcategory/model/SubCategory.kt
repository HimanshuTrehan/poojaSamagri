package com.slyked.admin.subcategory.model

data class SubCategory(
    val Category: Category,
    val category_id: Int,
    val createdAt: String,
    val id: Int,
    val image: Any,
    val is_default: Boolean,
    val name: String,
    val updatedAt: String
)
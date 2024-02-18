package com.slyked.admin.subcategory.model

data class Category(
    val createdAt: String,
    val id: Int,
    val image: String,
    val is_default: Boolean,
    val name: String,
    val updatedAt: String
)
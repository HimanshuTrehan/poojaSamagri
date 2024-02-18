package com.slyked.admin.product.model

data class Pagination(
    val currentPage: Int,
    val totalItems: Int,
    val totalPages: Int
)
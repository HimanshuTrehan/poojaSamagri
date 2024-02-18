package com.slyked.admin.models

data class Pagination(
    val currentPage: Int,
    val totalItems: Int,
    val totalPages: Int
)
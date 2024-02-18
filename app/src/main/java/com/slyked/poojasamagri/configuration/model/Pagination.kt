package com.slyked.admin.configuration.model

data class Pagination(
    val currentPage: Int,
    val totalItems: Int,
    val totalPages: Int
)
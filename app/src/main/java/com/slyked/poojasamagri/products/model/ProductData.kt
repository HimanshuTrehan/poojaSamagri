package com.slyked.admin.product.model

data class ProductData(
    val pagination: Pagination,
    val products: List<Product>
)
package com.slyked.admin.category.model

import com.slyked.admin.models.Pagination

data class CategoryData(
    val categories: Category,
    val pagination: Pagination
)
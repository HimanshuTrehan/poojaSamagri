package com.slyked.admin.category.model

import com.slyked.admin.models.Pagination

data class CategoryListData(
    val categories: List<Category>?,
    val pagination: Pagination?
)
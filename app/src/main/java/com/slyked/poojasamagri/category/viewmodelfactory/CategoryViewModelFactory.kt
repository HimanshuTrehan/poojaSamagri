package com.slyked.admin.category.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.slyked.admin.category.repository.CategoryRepository
import com.slyked.admin.category.viewmodel.CategoryViewModel

class CategoryViewModelFactory(private val categoryRepository: CategoryRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewModel(categoryRepository) as T
    }

}
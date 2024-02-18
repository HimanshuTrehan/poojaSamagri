package com.slyked.admin.subcategory.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.slyked.admin.category.repository.CategoryRepository
import com.slyked.admin.category.viewmodel.CategoryViewModel
import com.slyked.admin.subcategory.repository.SubCategoryRepository
import com.slyked.admin.subcategory.viewmodel.SubCategoryViewModel

class SubCategoryViewModelFactory(private val subCategoryRepository: SubCategoryRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SubCategoryViewModel(subCategoryRepository) as T
    }

}
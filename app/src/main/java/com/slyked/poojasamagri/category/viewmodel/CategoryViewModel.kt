package com.slyked.admin.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.slyked.admin.api.ResponseData
import com.slyked.admin.category.model.AllCategoryModel
import com.slyked.admin.category.model.Category
import com.slyked.admin.category.model.CategoryListData
import com.slyked.admin.category.repository.CategoryRepository
import com.slyked.admin.models.CommonResponse
import com.slyked.admin.paging.CategoryPagingSource
import com.slyked.admin.paging.ProductPagingSource
import com.slyked.admin.product.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class CategoryViewModel(private val repository: CategoryRepository):ViewModel() {

    val pagingConfig = PagingConfig(
        pageSize = 20,
        maxSize = 100
    )



    val getCategory: Flow<PagingData<com.slyked.admin.category.model.Category>> = Pager(config = pagingConfig) {
        CategoryPagingSource(categoryRepository = repository)
    }.flow.cachedIn(viewModelScope)





}
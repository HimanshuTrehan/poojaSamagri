package com.slyked.admin.category.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.slyked.admin.api.CategoryServices
import com.slyked.admin.api.ResponseData
import com.slyked.admin.category.model.Category
import com.slyked.admin.category.model.CategoryListData
import com.slyked.admin.models.CommonResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class CategoryRepository(private val categoryServices: CategoryServices) {

    suspend  fun getCategories(page:Int) = categoryServices.getCategories(page)







}
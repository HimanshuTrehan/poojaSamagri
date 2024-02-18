package com.slyked.admin.subcategory.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slyked.admin.api.ResponseData
import com.slyked.admin.models.CommonResponse
import com.slyked.admin.subcategory.model.SubCategory
import com.slyked.admin.subcategory.model.SubCategoryData
import com.slyked.admin.subcategory.model.SubCategoryModel
import com.slyked.admin.subcategory.repository.SubCategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class SubCategoryViewModel(private val repository: SubCategoryRepository) : ViewModel() {

    val subCategoryLiveData: LiveData<ResponseData<SubCategoryData>>
        get() = repository.allSubCategoriesLiveData

    val mediaType = "text/plain".toMediaType()



    fun getSubCategory(page:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllSubCategories(page)
        }

    }

  suspend  fun checkSubCategoryById(id: Int):List<SubCategory>? {

         val response =   repository.checkSubCategoryById(id)
        if (response?.data != null){
            return response.data.subcategories
        }
        return null
    }
    fun getSubCategoryById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSubCategoryById(id)
        }

    }



}
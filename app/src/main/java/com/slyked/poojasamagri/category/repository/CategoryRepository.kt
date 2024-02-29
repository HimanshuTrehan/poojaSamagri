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

    private val allCategoriesMutableLiveData = MutableLiveData<ResponseData<CategoryListData>>()

    val allCategoriesLiveData:LiveData<ResponseData<CategoryListData>>
        get()= allCategoriesMutableLiveData

    suspend  fun getCategories(page:Int) = categoryServices.getCategories(page)


    suspend fun getAllCategories()
    {

        allCategoriesMutableLiveData.postValue(ResponseData.Loading())
        val response =   categoryServices.getCategories(1)

        if (response.isSuccessful && response.body() !=null && response.body()!!.status == 200 && response.body()!!.data !=null &&  response.body()!!.data?.categories !=null)
        {
            allCategoriesMutableLiveData.postValue(ResponseData.Successful(response.body()!!.data))
        }else{
            allCategoriesMutableLiveData.postValue(ResponseData.Error(response.body()?.message ?: "An Error Occurred"))

        }


    }






}
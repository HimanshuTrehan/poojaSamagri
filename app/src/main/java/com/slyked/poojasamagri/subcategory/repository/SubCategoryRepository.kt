package com.slyked.admin.subcategory.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.slyked.admin.api.CategoryServices
import com.slyked.admin.api.ResponseData
import com.slyked.admin.api.SubCategoryServices
import com.slyked.admin.category.model.Category
import com.slyked.admin.category.model.CategoryListData
import com.slyked.admin.models.CommonResponse
import com.slyked.admin.subcategory.model.SubCategoryData
import com.slyked.admin.subcategory.model.SubCategoryModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class SubCategoryRepository(private val subCategoryServices: SubCategoryServices) {


    private val allSubCategoriesMutableLiveData = MutableLiveData<ResponseData<SubCategoryData>>()

    val allSubCategoriesLiveData:LiveData<ResponseData<SubCategoryData>>
    get()= allSubCategoriesMutableLiveData



    suspend fun getAllSubCategories(page:Int)
    {


        allSubCategoriesMutableLiveData.postValue(ResponseData.Loading())
            val response =   subCategoryServices.getSubCategories(page)

            if (response.isSuccessful && response.body() !=null && response.body()!!.status == 200 && response.body()!!.data !=null &&  response.body()!!.data?.subcategories !=null)
            {
                allSubCategoriesMutableLiveData.postValue(ResponseData.Successful(response.body()!!.data))
            }else{
                allSubCategoriesMutableLiveData.postValue(ResponseData.Error(response.body()?.message ?: "An Error Occurred"))

            }


    }

    suspend fun checkSubCategoryById(id: Int):SubCategoryModel?
    {


        val response =   subCategoryServices.getSubCategoriesById(id)

        if (response.isSuccessful && response.body() !=null && response.body()!!.status == 200 && response.body()!!.data !=null)
        {
            return response.body()!!
        }else{
            return null
        }


    }

    suspend fun getSubCategoryById(id: Int)
    {


        allSubCategoriesMutableLiveData.postValue(ResponseData.Loading())
        val response =   subCategoryServices.getSubCategoriesById(id)

        if (response.isSuccessful && response.body() !=null && response.body()!!.status == 200 && response.body()!!.data !=null &&  response.body()!!.data?.subcategories !=null)
        {
            allSubCategoriesMutableLiveData.postValue(ResponseData.Successful(response.body()!!.data))
        }else{
            allSubCategoriesMutableLiveData.postValue(ResponseData.Error(response.body()?.message ?: "An Error Occurred"))

        }


    }



}
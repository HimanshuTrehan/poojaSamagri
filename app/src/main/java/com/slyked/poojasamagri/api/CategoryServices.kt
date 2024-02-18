package com.slyked.admin.api

import com.slyked.admin.category.model.AllCategoryModel
import com.slyked.admin.models.CommonResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface CategoryServices {



    @GET("api/categories?pageSize=20")
    suspend  fun getCategories(@Query(value = "page") page:Int): Response<AllCategoryModel>

}
package com.slyked.admin.api

import com.slyked.admin.category.model.AllCategoryModel
import com.slyked.admin.models.CommonResponse
import com.slyked.admin.subcategory.model.SubCategoryModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface SubCategoryServices {



    @GET("api/sub-categories?pageSize=20")
    suspend  fun getSubCategories(@Query(value = "page") page:Int): Response<SubCategoryModel>

    @GET("api/sub-categories/")
    suspend  fun getSubCategoriesById(@Query("category_id") category_id: Int): Response<SubCategoryModel>


}
package com.slyked.admin.api

import com.slyked.admin.models.CommonResponse
import com.slyked.admin.product.model.AllProductModel
import com.slyked.admin.product.model.ProductModel
import com.slyked.admin.product.model.ProductResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ProductServices {



    @GET("/api/products?pageSize=20")
    suspend  fun getProducts(@Query(value = "page") page:Int):Response<AllProductModel>

    @GET("/api/products?pageSize=20")
    suspend  fun getProductsByName(@Query(value = "page") page:Int,@Query("search") search:String):Response<AllProductModel>

    @GET("/api/product/{id}")
    suspend  fun getProductsById(@Path(value = "id" ) id:Int):Response<ProductModel>


    @GET("/api/products?pageSize=20")
    suspend  fun getProductsByCategoryId(@Query(value = "page") page:Int,@Query(value = "category_id") categoryId:Int):Response<AllProductModel>

    @GET("/api/products?pageSize=20")
    suspend  fun getProductsBySubCategoryId(@Query(value = "page") page:Int,@Query(value = "sub_category_id") sub_category_id:Int):Response<AllProductModel>



}
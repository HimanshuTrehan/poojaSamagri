package com.slyked.admin.product.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.slyked.admin.api.ProductServices
import com.slyked.admin.api.ResponseData
import com.slyked.admin.models.CommonResponse
import com.slyked.admin.paging.ProductPagingSource
import com.slyked.admin.product.model.AllProductModel
import com.slyked.admin.product.model.Product
import com.slyked.admin.product.model.ProductModel
import com.slyked.admin.product.model.ProductResponseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class ProductRepository(private val productServices: ProductServices) {


    private val productsMutableLiveData = MutableLiveData<ResponseData<AllProductModel>>()

    val productsLiveData: LiveData<ResponseData<AllProductModel>>
        get() = productsMutableLiveData



//    suspend fun getProductsByName(page:Int,name:String) {
//        productsMutableLiveData.postValue(ResponseData.Loading())
//
//        val response = productServices.getProductsByName(page,name)
//        if (response.isSuccessful && response.body() != null && response.body()!!.status != null && response.body()!!.status == 200) {
//            productsMutableLiveData.postValue(ResponseData.Successful(response.body()))
//
//        } else {
//            productsMutableLiveData.postValue(
//                ResponseData.Error(
//                    response.body()?.message ?: "An Error Occurred"
//                )
//            )
//        }
//    }
   suspend  fun getProducts(page:Int) =productServices.getProducts(page)

    suspend  fun getProductsByName(page:Int,name:String)  = productServices.getProductsByName(page,name)

//    suspend fun getProducts(page:Int) {
//        productsMutableLiveData.postValue(ResponseData.Loading())
//
//        val response = productServices.getProducts(page)
//        if (response.isSuccessful && response.body() != null && response.body()!!.status != null && response.body()!!.status == 200) {
//            productsMutableLiveData.postValue(ResponseData.Successful(response.body()))
//
//        } else {
//            productsMutableLiveData.postValue(
//                ResponseData.Error(
//                    response.body()?.message ?: "An Error Occurred"
//                )
//            )
//        }
//    }
    suspend  fun getProductsByCategoryId(page:Int,id:Int)  = productServices.getProductsByCategoryId(page,id)

    suspend  fun getProductsBySubCategoryId(page:Int,id:Int)  = productServices.getProductsBySubCategoryId(page,id)

    suspend fun getProductsById(id: Int):Response<ProductModel> {
        val response = productServices.getProductsById(id)
       return response

    }



}
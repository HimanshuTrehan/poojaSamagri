package com.slyked.admin.product.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.google.gson.Gson
import com.slyked.admin.api.ResponseData
import com.slyked.admin.models.CommonResponse
import com.slyked.admin.paging.ProductPagingSource
import com.slyked.admin.product.model.*
import com.slyked.admin.product.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {


    val productData: LiveData<ResponseData<AllProductModel>>
        get() = productRepository.productsLiveData

    val pagingConfig = PagingConfig(
        pageSize = 20,
        maxSize = 100
    )



    val getProducts: Flow<PagingData<Product>> = Pager(config = pagingConfig) {
        ProductPagingSource(productRepository,"")
    }.flow.cachedIn(viewModelScope)
   // val getProducts = Pager(pagingConfig , pagingSourceFactory ={ ProductPagingSource(productRepository,"") }).liveData


    suspend fun getProductsById(id: Int): Response<ProductModel> {
        val response = productRepository.getProductsById(id)
        return response


    }


    fun getProductsByName(name:String):
            Flow<PagingData<Product>> {
        return Pager(config = pagingConfig) {
            ProductPagingSource(productRepository,name)
        }.flow.cachedIn(viewModelScope)

    }

    fun getProductsByCategoryId(id:Int):
            Flow<PagingData<Product>> {
        return Pager(config = pagingConfig) {
            ProductPagingSource(productRepository, categoryId = id)
        }.flow.cachedIn(viewModelScope)

    }

    fun getProductsBySubCategoryId(id:Int):
            Flow<PagingData<Product>> {
        return Pager(config = pagingConfig) {
            ProductPagingSource(productRepository, subCategoryId = id)
        }.flow.cachedIn(viewModelScope)

    }



}
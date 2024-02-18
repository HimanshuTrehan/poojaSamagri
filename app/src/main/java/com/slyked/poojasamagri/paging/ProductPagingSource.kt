package com.slyked.admin.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.slyked.admin.api.ProductServices
import com.slyked.admin.product.model.AllProductModel
import com.slyked.admin.product.model.Product
import com.slyked.admin.product.repository.ProductRepository
import retrofit2.Response

class ProductPagingSource(private val productAPI: ProductRepository, private  val searchByName:String="",private  val categoryId:Int =0,private  val subCategoryId:Int=0) : PagingSource<Int,Product>() {



    override suspend fun load(params: androidx.paging.PagingSource.LoadParams<Int>): androidx.paging.PagingSource.LoadResult<Int, Product> {
        return try {
            val position = params.key ?: 1
            var response : Response<AllProductModel>? = null
            if(searchByName.isNotEmpty()){
                response = productAPI.getProductsByName(position, name = searchByName)
            }else if(categoryId!=0){
                response = productAPI.getProductsByCategoryId(position, categoryId)

            }else if(subCategoryId!=0){
                response = productAPI.getProductsBySubCategoryId(position, subCategoryId)

            }

            else {
                 response = productAPI.getProducts(position)
            }
            return androidx.paging.PagingSource.LoadResult.Page(
                data = response.body()?.data!!.products,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.body()!!.data.pagination.totalPages) null else position + 1
            )
        } catch (e: Exception) {
            androidx.paging.PagingSource.LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}
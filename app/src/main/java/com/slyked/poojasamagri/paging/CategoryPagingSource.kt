package com.slyked.admin.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.slyked.admin.category.model.AllCategoryModel
import com.slyked.admin.category.model.Category
import com.slyked.admin.category.repository.CategoryRepository

import retrofit2.Response

class CategoryPagingSource(private val categoryRepository: CategoryRepository) : PagingSource<Int, Category>() {

    override suspend fun load(params: androidx.paging.PagingSource.LoadParams<Int>): androidx.paging.PagingSource.LoadResult<Int, Category> {
        return try {
            val position = params.key ?: 1
            var response : Response<AllCategoryModel>? = null

                 response = categoryRepository.getCategories(position)

            return androidx.paging.PagingSource.LoadResult.Page(
                data = response.body()?.data!!.categories!!,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == (response.body()!!.data?.pagination?.totalPages ?: 1)) null else position + 1
            )
        } catch (e: Exception) {
            androidx.paging.PagingSource.LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Category>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}
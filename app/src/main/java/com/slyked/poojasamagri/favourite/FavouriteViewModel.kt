package com.slyked.poojasamagri.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slyked.admin.api.ResponseData
import com.slyked.admin.product.model.FavouriteProduct
import com.slyked.poojasamagri.products.dao.FavouriteProductDao
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteViewModel @Inject constructor(private val favouriteProductDao: FavouriteProductDao) : ViewModel() {

    private val mutableLiveData = MutableLiveData<ResponseData<List<FavouriteProduct>>>()

     val favouriteList:LiveData<ResponseData<List<FavouriteProduct>>>
    get() = mutableLiveData

     suspend fun getFavouriteList()
    {
        try{
            mutableLiveData.postValue(ResponseData.Loading())
            val response = favouriteProductDao.getFavouriteProducts()
            mutableLiveData.postValue(ResponseData.Successful(response))

        }
        catch (e:java.lang.Exception){
            mutableLiveData.postValue(ResponseData.Error(e.toString()))

        }

    }

    suspend fun deleteFavouriteProduct(id:Int){
        viewModelScope.launch {
          val response = async {   favouriteProductDao.deleteFavouriteProduct(id) }

            if (response.await() == 1){
                getFavouriteList()
            }

        }
    }



}
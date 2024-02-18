package com.slyked.poojasamagri.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slyked.admin.api.ResponseData
import com.slyked.admin.product.model.CartProduct
import com.slyked.admin.product.model.FavouriteProduct
import com.slyked.poojasamagri.products.dao.CartProductDao
import com.slyked.poojasamagri.products.dao.FavouriteProductDao
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartViewModel @Inject constructor(private val cartProductDao: CartProductDao) : ViewModel() {

    private val mutableLiveData = MutableLiveData<ResponseData<List<CartProduct>>>()

     val cartList:LiveData<ResponseData<List<CartProduct>>>
    get() = mutableLiveData

   private var selectedQuantity = 1

    fun addSelectedQuantity(){

        selectedQuantity++
    }
    fun deleteSelectedQuantity(){
        if (selectedQuantity>1) {
            --selectedQuantity
        }
    }

    fun getSelectedQuantity():Int{
        return selectedQuantity
    }


    suspend  fun getCartList()
    {
        try{
            mutableLiveData.postValue(ResponseData.Loading())
            val response = cartProductDao.getCartProducts()
            mutableLiveData.postValue(ResponseData.Successful(response))

        }
        catch (e:java.lang.Exception){
            mutableLiveData.postValue(ResponseData.Error(e.toString()))

        }

    }

     fun deleteCartItem(id:Int){
        viewModelScope.launch {
          val response = async {   cartProductDao.deleteCartProduct(id) }

            if (response.await() == 1){
                getCartList()
            }

        }
    }



}
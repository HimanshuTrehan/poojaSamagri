package com.slyked.admin.product.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.slyked.admin.product.repository.ProductRepository
import com.slyked.admin.product.viewmodel.ProductViewModel
import com.slyked.poojasamagri.cart.CartViewModel
import com.slyked.poojasamagri.favourite.FavouriteViewModel
import com.slyked.poojasamagri.products.dao.CartProductDao
import com.slyked.poojasamagri.products.dao.FavouriteProductDao
import javax.inject.Inject

class CartViewModelFactory @Inject constructor(private val cartProductDao: CartProductDao): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CartViewModel(cartProductDao) as T
    }

}
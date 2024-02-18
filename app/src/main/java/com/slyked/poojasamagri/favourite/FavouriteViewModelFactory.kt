package com.slyked.admin.product.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.slyked.admin.product.repository.ProductRepository
import com.slyked.admin.product.viewmodel.ProductViewModel
import com.slyked.poojasamagri.favourite.FavouriteViewModel
import com.slyked.poojasamagri.products.dao.FavouriteProductDao
import javax.inject.Inject

class FavouriteViewModelFactory @Inject constructor(private val favouriteProductDao: FavouriteProductDao): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavouriteViewModel(favouriteProductDao) as T
    }

}
package com.slyked.poojasamagri.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.slyked.admin.api.ResponseData
import com.slyked.admin.product.model.FavouriteProduct
import com.slyked.admin.product.model.Product
import com.slyked.admin.product.viewmodelfactory.FavouriteViewModelFactory
import com.slyked.poojasamagri.databinding.FragmentFavouriteBinding
import com.slyked.poojasamagri.favourite.FavouriteAdapter
import com.slyked.poojasamagri.favourite.FavouriteViewModel
import com.slyked.poojasamagri.products.dao.FavouriteProductDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavouriteFragment : Fragment(), FavouriteAdapter.ProductListener {
    var favouriteProductList : List<FavouriteProduct> = arrayListOf()
    lateinit var binding: FragmentFavouriteBinding
    lateinit var favouriteListAdapter: FavouriteAdapter
    lateinit var favouriteViewModel: FavouriteViewModel
    @Inject
    lateinit var  favouriteProductDao: FavouriteProductDao
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = FragmentFavouriteBinding.inflate(inflater,container,false)
        setupViewModel()
      //  setCategoryRecycler()
        getFavouriteProducts()

        return binding.root
    }

    private fun setupViewModel() {
        favouriteViewModel = ViewModelProvider(this,FavouriteViewModelFactory(favouriteProductDao)).get(FavouriteViewModel::class.java)
    }

    private fun getFavouriteProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            favouriteViewModel.getFavouriteList()

        }
        favouriteViewModel.favouriteList.observe(requireActivity()){
            when(it){
                is ResponseData.Loading -> {
                    binding.progressBar.visibility = VISIBLE

                }
                is ResponseData.Successful -> {
                    binding.progressBar.visibility = GONE
                    if (it.data?.size!! >0)
                    {
                        favouriteProductList = it.data
                        setCategoryRecycler()
                    } else {
                        binding.favRecyclerView.visibility = View.GONE
                        binding.notFound.visibility = View.VISIBLE

                    }

                }
                is ResponseData.Error -> {
                    binding.notFound.visibility = View.VISIBLE
                    binding.progressBar.visibility = GONE
                    binding.favRecyclerView.visibility = GONE

                }
            }


        }

    }

    private fun setCategoryRecycler() {
        binding.favRecyclerView.layoutManager = GridLayoutManager(context,2)
        favouriteListAdapter = FavouriteAdapter(requireContext(),favouriteProductList,this)
        binding.favRecyclerView.adapter = favouriteListAdapter

    }

    override fun removeFromFavourite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            favouriteViewModel.deleteFavouriteProduct(id)

        }
    }

    override fun onClick(id: Int) {
    }


}
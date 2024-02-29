package com.slyked.poojasamagri.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.slyked.admin.api.ProductServices
import com.slyked.admin.api.ResponseData
import com.slyked.admin.api.RetrofitHelper
import com.slyked.admin.product.model.CartProduct
import com.slyked.admin.product.model.FavouriteProduct
import com.slyked.admin.product.model.Product
import com.slyked.admin.product.model.ProductsVariant
import com.slyked.admin.product.repository.ProductRepository
import com.slyked.admin.product.viewmodel.ProductViewModel
import com.slyked.admin.product.viewmodelfactory.FavouriteViewModelFactory
import com.slyked.admin.product.viewmodelfactory.ProductViewModelFactory
import com.slyked.poojasamagri.databinding.FragmentFavouriteBinding
import com.slyked.poojasamagri.favourite.FavouriteAdapter
import com.slyked.poojasamagri.favourite.FavouriteViewModel
import com.slyked.poojasamagri.products.dao.CartProductDao
import com.slyked.poojasamagri.products.dao.FavouriteProductDao
import com.slyked.poojasamagri.products.ui.ProductDetailsActivity
import com.slyked.poojasamagri.utils.ProductVariantsSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavouriteFragment : Fragment(), FavouriteAdapter.ProductListener,ProductVariantsSheet.BottomSheetListeners {
    var favouriteProductList : List<FavouriteProduct> = arrayListOf()
    lateinit var binding: FragmentFavouriteBinding
    lateinit var favouriteListAdapter: FavouriteAdapter
    lateinit var favouriteViewModel: FavouriteViewModel
    lateinit var productViewModel: ProductViewModel
    var productData:Product? = null
    @Inject
    lateinit var  cartDao: CartProductDao
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
        val productService = RetrofitHelper.getInstance().create(ProductServices::class.java)
        val productRepository = ProductRepository(productService)
        productViewModel = ViewModelProvider(
            this,
            ProductViewModelFactory(productRepository)
        )[ProductViewModel::class.java]
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
        val intent = Intent(requireContext(), ProductDetailsActivity::class.java)
        intent.putExtra("productId", id)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        requireContext().startActivity(intent)
    }

    override fun openVariants(id: Int) {
        openBottomSheet(id)
    }

    private fun openBottomSheet(id:Int) {
        binding.progressBar.visibility = VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            var response = async {   productViewModel.getProductsById(id) }
            if (response.await().isSuccessful && response.await().body() != null) {
                productData = response.await().body()!!.data?.product
                requireActivity().runOnUiThread {
                    binding.progressBar.visibility = GONE

                    ProductVariantsSheet(requireActivity(),productData!!,this@FavouriteFragment).showDialog()

                }
                //setRadioButton()
            }
        }


    }

    override fun addToCart(productData: Product, data: MutableMap<Int, Int>) {
        CoroutineScope(Dispatchers.IO).launch {
            var cartList = arrayListOf<CartProduct>()
            for (variants in data) {
                var variant = getVariantFromId(variants.key, productData)
                cartList.add(
                    CartProduct(
                        productId = productData.id,
                        description = productData.description,
                        variant = variant,
                        image = productData.image,
                        quantity = variants.value,
                        name = productData.name
                    )
                )
            }
            cartDao.addToCartProductList(cartList)
        }
    }

    private fun getVariantFromId(key: Int, productData: Product): ProductsVariant? {

        var listVariants = productData.ProductsVariants

        if (listVariants != null) {
            for (data in listVariants){
                if (data.id == key)
                {
                    return data
                }
            }
        }
        return null
    }


}
package com.slyked.poojasamagri.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.filter
import androidx.recyclerview.widget.GridLayoutManager
import com.slyked.admin.api.ProductServices
import com.slyked.admin.api.ResponseData
import com.slyked.admin.api.RetrofitHelper
import com.slyked.admin.product.model.Product
import com.slyked.admin.product.repository.ProductRepository
import com.slyked.admin.product.viewmodel.ProductViewModel
import com.slyked.admin.product.viewmodelfactory.ProductViewModelFactory
import com.slyked.poojasamagri.products.adapter.ProductAdapter
import com.slyked.poojasamagri.databinding.ActivitySearchBinding
import com.slyked.poojasamagri.products.dao.FavouriteProductDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(),ProductAdapter.ProductListener {
    lateinit var binding: ActivitySearchBinding
    lateinit var productAdapter: ProductAdapter
    lateinit var productViewModel: ProductViewModel
    var productList : List<Product> = arrayListOf()
    @Inject
    lateinit var  favouriteProductDao: FavouriteProductDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createViewModel()
        setClickListeners()
        setProductRecycler()

    }

    private fun setClickListeners() {

        productViewModel.productData.observe(this){
            when(it)
            {
                is ResponseData.Loading ->{
                    binding.progressBar.visibility = View.VISIBLE
                    binding.notFound.visibility = View.GONE

                }
                is ResponseData.Successful -> {
                    binding.progressBar.visibility = View.GONE
                    if (it.data?.data?.products!!.isNotEmpty())
                    {
                        binding.productRecyclerView.visibility = View.VISIBLE

                        productList = it.data.data.products
                        setProductRecycler()
                    }else{
                        binding.productRecyclerView.visibility = View.GONE
                        binding.notFound.visibility = View.VISIBLE
                    }

                }
                is ResponseData.Error ->{
                    binding.progressBar.visibility = View.GONE
                    binding.notFound.visibility = View.VISIBLE

                }
            }
        }


        binding.searchView.setOnEditorActionListener { textView, actionId, keyEvent ->

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Hide Keyboard
                val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(textView.windowToken, 0)
              //  productViewModel.getProductsByName(textView.text.toString())
                getProductsByName(textView.text.toString())

                true
            }
            false
        }
        binding.back.setOnClickListener {
            finish()
        }
    }

    private fun getProductsByName(name: String) {
        lifecycleScope.launch {
            productViewModel.getProductsByName(name).collectLatest { pagingData ->
                //   pagingData to productData
                // println("Recycler " + productData.size)
                productAdapter.submitData(pagingData)

            }
        }
    }

    private fun createViewModel() {
        val service = RetrofitHelper.getInstance().create(ProductServices::class.java)

        val repository = ProductRepository(service)

        productViewModel = ViewModelProvider(this, ProductViewModelFactory(repository)).get(
            ProductViewModel::class.java)
    }
    private fun setProductRecycler() {
        binding.productRecyclerView.layoutManager = GridLayoutManager(applicationContext,2)
        productAdapter = ProductAdapter(applicationContext,favouriteProductDao, this)
        binding.productRecyclerView.adapter = productAdapter
    }

    override fun onResume() {
        super.onResume()
        getProducts()
    }

    private fun getProducts() {
        setProductRecycler()
        lifecycleScope.launch {
            productViewModel.getProducts.collectLatest { pagingData ->
             //   pagingData to productData
               // println("Recycler " + productData.size)
                productAdapter.submitData(pagingData)
            }
        }
//        productViewModel.getProducts.observe(this){
//
//
//
//            //  binding.progressBar.visibility = GONE
//            productAdapter.submitData( lifecycle, it)
//            lifecycleScope.launch {
//
//            }
//
//        }
        lifecycleScope.launch {
            productAdapter.loadStateFlow.collect{
                val state = it.refresh

                if(state is LoadState.Loading){
                    binding.progressBar.visibility = View.VISIBLE
                }else{
                    binding.progressBar.visibility = View.GONE

                }

            }
        }


        productAdapter.addLoadStateListener { combinedLoadStates ->
            // If you don't want to call all the time, you
            // can filter on changes in combinedLoadStates
            println("Recycler addLoadStateListener " + productAdapter.itemCount)

            // shopViewModel.setItemAmount(shopListAdapter.itemCount)
        }

    }

    override fun addToFavourite(id: Int) {

    }

    override fun onClick(id: Int) {

    }
}
package com.slyked.poojasamagri.products.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagedList
import androidx.paging.filter
import androidx.paging.flatMap
import androidx.recyclerview.widget.GridLayoutManager
import com.slyked.admin.api.ProductServices
import com.slyked.admin.api.ResponseData
import com.slyked.admin.api.RetrofitHelper
import com.slyked.admin.product.model.FavouriteProduct
import com.slyked.admin.product.model.Product
import com.slyked.admin.product.repository.ProductRepository
import com.slyked.admin.product.viewmodel.ProductViewModel
import com.slyked.admin.product.viewmodelfactory.ProductViewModelFactory
import com.slyked.poojasamagri.products.adapter.ProductAdapter
import com.slyked.poojasamagri.databinding.ActivityViewAllProductsBinding
import com.slyked.poojasamagri.products.dao.FavouriteProductDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ViewAllProducts : AppCompatActivity(),ProductAdapter.ProductListener {

    lateinit var binding:ActivityViewAllProductsBinding
    lateinit var productAdapter: ProductAdapter
    var productList : List<Product?> = arrayListOf()
    lateinit var productViewModel: ProductViewModel
    var productData:List<Product> = arrayListOf()
    @Inject
    lateinit var  favouriteProductDao: FavouriteProductDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAllProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createViewModel()
        setProductRecycler()
        getIntentData()
        binding.back.setOnClickListener {
            finish()
        }
    }

    private fun getIntentData() {
       val categoryId = intent.getIntExtra("categoryId", 0)
       val subCategoryId = intent.getIntExtra("subCategoryId", 0)
        if (categoryId !=0)
        {
            getProductByCategoryId(categoryId)
        }else if(subCategoryId !=0){
            getProductBySubCategoryId(subCategoryId)
        }else{
            getProductData()
        }
    }

    private fun getProductByCategoryId(categoryId: Int) {
        lifecycleScope.launch {
            productViewModel.getProductsByCategoryId(categoryId).collectLatest { pagingData ->
                //   pagingData to productData
                // println("Recycler " + productData.size)
                productAdapter.submitData(pagingData)
            }
        }
        addProductDataListeners()


    }
    private fun getProductBySubCategoryId(subCategoryId: Int) {
        lifecycleScope.launch {
            productViewModel.getProductsBySubCategoryId(subCategoryId).collectLatest { pagingData ->
                //   pagingData to productData
                // println("Recycler " + productData.size)
                productAdapter.submitData(pagingData)
            }
        }
        addProductDataListeners()


    }

    private fun createViewModel() {
        val service = RetrofitHelper.getInstance().create(ProductServices::class.java)

        val repository = ProductRepository(service)

        productViewModel = ViewModelProvider(this, ProductViewModelFactory(repository)).get(
            ProductViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()

    }



    private fun getProductData() {
//        productViewModel.getProducts.observe(this){
//

//
//            //  binding.progressBar.visibility = GONE
//            productAdapter.submitData( lifecycle, it)
//        //   println("Recycler Count" + productAdapter.itemCount)
//
//        }
        lifecycleScope.launch {
            productViewModel.getProducts.collectLatest { pagingData ->
                //   pagingData to productData
                // println("Recycler " + productData.size)
                productAdapter.submitData(pagingData)
            }
        }
        addProductDataListeners()
//            productViewModel.getProducts.observe(this) {
//
//                setProductRecycler()
//
//                //  binding.progressBar.visibility = GONE
//                productAdapter.submitData(lifecycle, it)
//                lifecycleScope.launch {
//
//                }
//
//            }


    }

    private fun addProductDataListeners(){
        lifecycleScope.launch {
            productAdapter.loadStateFlow.collect{
                val state = it.refresh
                if(state is LoadState.Loading){
                    binding.notFound.visibility = GONE
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
            if (productAdapter.itemCount == 0)
            {
                binding.notFound.visibility = VISIBLE
            }else{
                binding.notFound.visibility = GONE

            }
            if (productAdapter.itemCount>0) {

                productList = productAdapter.snapshot().toList()
            }

            // shopViewModel.setItemAmount(shopListAdapter.itemCount)
        }
    }

    private fun setProductRecycler() {
        binding.productRecyclerView.layoutManager = GridLayoutManager(applicationContext,2)
        productAdapter = ProductAdapter(applicationContext,favouriteProductDao,this)
        binding.productRecyclerView.adapter = productAdapter
    }

    override fun addToFavourite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val product = getProductFromId(id)
            if (product !=null) {
                if (favouriteProductDao.isItemExist(id.toString())) {

                    favouriteProductDao.deleteFavouriteProduct(id)


                }else{
                    favouriteProductDao.addFavouriteProduct(product)


                }
                runOnUiThread {
                    productAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun getProductFromId(id: Int): FavouriteProduct? {
        for(products in productList)
        {
            if (products?.id == id)
            {
                return FavouriteProduct(id,products.ProductsVariants!!,products.description,products.image, name =products.name,products.out_of_stock )
            }
        }
        return null
    }

    override fun onClick(id: Int) {
        openProductDetailsPage(id)
    }
    private fun openProductDetailsPage(id:Int) {
        val intent = Intent(applicationContext , ProductDetailsActivity::class.java)
        intent.putExtra("productId", id);
        startActivity(intent)
    }
}
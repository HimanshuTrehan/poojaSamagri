package com.slyked.poojasamagri.products.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.slyked.admin.api.ProductServices
import com.slyked.admin.api.RetrofitHelper
import com.slyked.admin.product.model.CartProduct
import com.slyked.admin.product.model.FavouriteProduct
import com.slyked.admin.product.model.Product
import com.slyked.admin.product.model.ProductsVariant
import com.slyked.admin.product.repository.ProductRepository
import com.slyked.admin.product.viewmodel.ProductViewModel
import com.slyked.admin.product.viewmodelfactory.ProductViewModelFactory
import com.slyked.poojasamagri.products.adapter.ProductAdapter
import com.slyked.poojasamagri.databinding.ActivityViewAllProductsBinding
import com.slyked.poojasamagri.products.dao.CartProductDao
import com.slyked.poojasamagri.products.dao.FavouriteProductDao
import com.slyked.poojasamagri.utils.ProductVariantsSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ViewAllProducts : AppCompatActivity(),ProductAdapter.ProductListener,ProductVariantsSheet.BottomSheetListeners {

    lateinit var binding:ActivityViewAllProductsBinding
    lateinit var productAdapter: ProductAdapter
    var productList : List<Product?> = arrayListOf()
    lateinit var productViewModel: ProductViewModel
    var productData:List<Product> = arrayListOf()
    @Inject
    lateinit var  favouriteProductDao: FavouriteProductDao
    @Inject
    lateinit var  cartDao: CartProductDao
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
            val product = getFavouriteProductFromId(id)
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

    private fun getFavouriteProductFromId(id: Int): FavouriteProduct? {
        for(products in productList)
        {
            if (products?.id == id)
            {
                return FavouriteProduct(id,products.ProductsVariants!!,products.description,products.image, name =products.name,products.out_of_stock )
            }
        }
        return null
    }
    private fun getProductFromId(id: Int): Product? {
        for(products in productList)
        {
            if (products?.id == id)
            {
                return products
            }
        }
        return null
    }


    override fun onClick(id: Int) {
        openProductDetailsPage(id)
    }

    override fun openVariants(id: Int) {
        openBottomSheet(id)
    }

    private fun openBottomSheet(id:Int) {
        var product = getProductFromId(id)
        ProductVariantsSheet(this,product!!,this).showDialog()

    }

    private fun openProductDetailsPage(id:Int) {
        val intent = Intent(applicationContext , ProductDetailsActivity::class.java)
        intent.putExtra("productId", id);
        startActivity(intent)
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
package com.slyked.poojasamagri.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slyked.admin.api.CategoryServices
import com.slyked.admin.api.ProductServices
import com.slyked.admin.api.ResponseData
import com.slyked.admin.api.RetrofitHelper
import com.slyked.admin.category.model.Category
import com.slyked.admin.category.repository.CategoryRepository
import com.slyked.admin.category.viewmodel.CategoryViewModel
import com.slyked.admin.category.viewmodelfactory.CategoryViewModelFactory
import com.slyked.admin.product.model.FavouriteProduct
import com.slyked.admin.product.model.Product
import com.slyked.admin.product.repository.ProductRepository
import com.slyked.admin.product.viewmodel.ProductViewModel
import com.slyked.admin.product.viewmodelfactory.ProductViewModelFactory
import com.slyked.poojasamagri.PoojaApplication
import com.slyked.poojasamagri.adapter.BannerAdapter
import com.slyked.poojasamagri.category.adapter.CategoryAdapter
import com.slyked.poojasamagri.products.adapter.HomeProductListAdapter
import com.slyked.poojasamagri.databinding.FragmentHomeBinding
import com.slyked.poojasamagri.category.ui.CategoryListActivity
import com.slyked.poojasamagri.db.AppDataBase
import com.slyked.poojasamagri.products.dao.FavouriteProductDao
import com.slyked.poojasamagri.products.ui.ProductDetailsActivity
import com.slyked.poojasamagri.ui.SearchActivity
import com.slyked.poojasamagri.products.ui.ViewAllProducts
import com.slyked.poojasamagri.utils.CirclePagerIndicatorDecoration
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(),HomeProductListAdapter.ProductListener {

    lateinit var binding: FragmentHomeBinding
    lateinit var product_adapter: HomeProductListAdapter
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var banner_adapter: BannerAdapter
    lateinit var productViewModel:ProductViewModel
    lateinit var categoryViewModel:CategoryViewModel
    var productList : List<Product?> = arrayListOf()
     var categoryItems: List<Category?> = arrayListOf()
    @Inject
   lateinit var  favouriteProductDao:FavouriteProductDao
    var position = -1

    init {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
       println("Lifecycle onAttach")

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Lifecycle onCreate")

        setupViewModels()

      //  getProductData()
       // getCategoryData()
      //  setCategoryRecycler()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("Lifecycle onCreateView")

        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false);
     //   setCategoryRecycler()
        setAdBannerRecycler()
       // setProductRecycler()
        setClickListeners()

        return binding.root;

    }

    override fun onResume() {
        super.onResume()
        println("Lifecycle onResume")
        getProductData()
        getCategoryData()

    }



    private fun getCategoryData() {
        setCategoryRecycler()
        lifecycleScope.launch {
            categoryViewModel.getCategory.collectLatest {
                categoryAdapter.submitData(lifecycle,it)
            }
        }

        categoryAdapter.addLoadStateListener { combinedLoadStates ->
            // If you don't want to call all the time, you
            // can filter on changes in combinedLoadStates
            println("Recycler addLoadStateListener " + categoryAdapter.itemCount)
            if (categoryAdapter.itemCount>0) {

                categoryItems = categoryAdapter.snapshot().toList()
              //  dismissLottieView()
            }

            // shopViewModel.setItemAmount(shopListAdapter.itemCount)
        }



    }

    private fun getProductData() {
        setProductRecycler()
        lifecycleScope.launch {
            productViewModel.getProducts.collectLatest { pagingData ->
                //   pagingData to productData
                // println("Recycler " + productData.size)
                product_adapter.submitData(pagingData)
            }
        }


        product_adapter.addLoadStateListener { combinedLoadStates ->
            // If you don't want to call all the time, you
            // can filter on changes in combinedLoadStates
            println("Recycler addLoadStateListener " + product_adapter.itemCount)
            if (product_adapter.itemCount>0) {

              productList = product_adapter.snapshot().toList()
                dismissLottieView()
            }

            // shopViewModel.setItemAmount(shopListAdapter.itemCount)
        }
//        lifecycleScope.launch {
//            productViewModel.getProducts.observe(requireActivity()) {
//
//              //  setProductRecycler()
//
//                //  binding.progressBar.visibility = GONE
//                product_adapter.submitData(lifecycle, it)
//            }
//        }


        lifecycleScope.launch {
            product_adapter.loadStateFlow.collect{
                val state = it.refresh
                if(state is LoadState.Loading){
                 //   binding.progressBar.visibility = View.VISIBLE
                }else{
                //    binding.progressBar.visibility = View.GONE

                }

            }
        }
//        if (productList.isEmpty()) {
//            productViewModel.getProducts(1)
//        }
//            productViewModel.productData.observe(this) {
//                when (it) {
//                    is ResponseData.Loading -> {
//                        println("loading")
////                    binding.progressBar.visibility = View.VISIBLE
////                    binding.notFound.visibility = View.GONE
//
//                    }
//                    is ResponseData.Successful -> {
//                        //   binding.progressBar.visibility = View.GONE
//                        if (it.data?.data?.products!!.isNotEmpty()) {
//                            binding.productRecyclerView.visibility = View.VISIBLE
//
//                            productList = it.data.data.products
//                            setProductRecycler()
//                        } else {
//                            binding.productRecyclerView.visibility = View.GONE
//                            //  binding.notFound.visibility = View.VISIBLE
//                        }
//
//                    }
//                    is ResponseData.Error -> {
//                        //   binding.progressBar.visibility = View.GONE
//                        //  binding.notFound.visibility = View.VISIBLE
//
//                    }
//                }
//            }
        }

    private fun dismissLottieView() {
        binding.animationView.cancelAnimation()
        binding.animationView.visibility =GONE
    }


    private fun setupViewModels() {
        val productService = RetrofitHelper.getInstance().create(ProductServices::class.java)
        val productRepository = ProductRepository(productService)
        productViewModel = ViewModelProvider(this,ProductViewModelFactory(productRepository))[ProductViewModel::class.java]

        val categoryService = RetrofitHelper.getInstance().create(CategoryServices::class.java)
        val categoryRepository = CategoryRepository(categoryService)
        categoryViewModel = ViewModelProvider(this,CategoryViewModelFactory(categoryRepository))[CategoryViewModel::class.java]
    }




    private fun setCategoryRecycler() {
        binding.categoryRecycler.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter = CategoryAdapter(requireContext(),categoryItems)
        binding.categoryRecycler.adapter = categoryAdapter
    }

    private fun setClickListeners() {
        binding.viewAllProductTxt.setOnClickListener {
            val intent = Intent(requireContext(), ViewAllProducts::class.java)
            startActivity(intent)
        }

        binding.searchView.setOnClickListener {
            val intent = Intent(requireContext(),SearchActivity::class.java)
            startActivity(intent)
        }
        binding.viewAllCategoryTxt.setOnClickListener {
            val intent = Intent(requireContext(), CategoryListActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setAdBannerRecycler() {

        binding.adBanner.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
        //        recyclerView.setOnFlingListener(null);
      //  if (contentList.size > 1) {
            binding.adBanner.addItemDecoration(CirclePagerIndicatorDecoration())
       // }
        binding.adBanner.setItemAnimator(DefaultItemAnimator())
        banner_adapter = BannerAdapter(requireContext())
        binding.adBanner.adapter = banner_adapter
        val mHandler = Handler(Looper.getMainLooper())
        val SCROLLING_RUNNABLE: Runnable = object : Runnable {
            override fun run() {
                position++
                if (position < 4 )//contentList.size)
                     {
                    binding.adBanner.smoothScrollToPosition(position)
                } else if (position == 4) {
                    position = 0
                    binding.adBanner.smoothScrollToPosition(0)
                }
                mHandler.postDelayed(this, 3000)
            }
        }
        mHandler.postDelayed(SCROLLING_RUNNABLE, 1000)
    }

    private fun setProductRecycler() {
        binding.productRecyclerView.layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
        product_adapter = HomeProductListAdapter(requireContext(),favouriteProductDao,this)
       // binding.productRecyclerView.setNestedScrollingEnabled(false);
        binding.productRecyclerView.adapter = product_adapter
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
                requireActivity().runOnUiThread {
                    product_adapter.notifyDataSetChanged()
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
        val intent = Intent(requireContext() , ProductDetailsActivity::class.java)
        intent.putExtra("productId", id)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        requireContext().startActivity(intent)
    }


}
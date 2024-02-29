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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slyked.admin.api.*
import com.slyked.admin.category.model.Category
import com.slyked.admin.category.repository.CategoryRepository
import com.slyked.admin.category.viewmodel.CategoryViewModel
import com.slyked.admin.category.viewmodelfactory.CategoryViewModelFactory
import com.slyked.admin.configuration.model.Banner
import com.slyked.admin.configuration.repository.ConfigurationRepository
import com.slyked.admin.configuration.viewmodel.ConfigurationViewModel
import com.slyked.admin.configuration.viewmodelfactory.ConfigurationViewModelFactory
import com.slyked.admin.product.model.CartProduct
import com.slyked.admin.product.model.FavouriteProduct
import com.slyked.admin.product.model.Product
import com.slyked.admin.product.model.ProductsVariant
import com.slyked.admin.product.repository.ProductRepository
import com.slyked.admin.product.viewmodel.ProductViewModel
import com.slyked.admin.product.viewmodelfactory.ProductViewModelFactory
import com.slyked.poojasamagri.AppConfiguration
import com.slyked.poojasamagri.adapter.BannerAdapter
import com.slyked.poojasamagri.category.adapter.CategoryAdapter
import com.slyked.poojasamagri.category.ui.CategoryListActivity
import com.slyked.poojasamagri.databinding.FragmentHomeBinding
import com.slyked.poojasamagri.products.adapter.HomeProductListAdapter
import com.slyked.poojasamagri.products.adapter.ProductAdapter
import com.slyked.poojasamagri.products.dao.CartProductDao
import com.slyked.poojasamagri.products.dao.FavouriteProductDao
import com.slyked.poojasamagri.products.ui.ProductDetailsActivity
import com.slyked.poojasamagri.products.ui.ViewAllProducts
import com.slyked.poojasamagri.ui.NotificationActivity
import com.slyked.poojasamagri.ui.SearchActivity
import com.slyked.poojasamagri.utils.CirclePagerIndicatorDecoration
import com.slyked.poojasamagri.utils.ProductVariantsSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment(), ProductAdapter.ProductListener,ProductVariantsSheet.BottomSheetListeners {

    lateinit var binding: FragmentHomeBinding
    lateinit var product_adapter: ProductAdapter
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var banner_adapter: BannerAdapter
    lateinit var productViewModel: ProductViewModel
    lateinit var categoryViewModel: CategoryViewModel
    lateinit var configurationViewModel: ConfigurationViewModel
    var productList: List<Product?> = arrayListOf()
    var categoryItems: List<Category?> = arrayListOf()
    var bannerItems: List<Banner?> = arrayListOf()
    @Inject
    lateinit var  cartDao: CartProductDao
    @Inject
    lateinit var favouriteProductDao: FavouriteProductDao
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
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        //   setCategoryRecycler()

        // setProductRecycler()
        setClickListeners()
        return binding.root;

    }

    override fun onResume() {
        super.onResume()
        println("Lifecycle onResume")
        getProductData()
        getCategoryData()
        getBannerData()

    }

    private fun getBannerData() {
        configurationViewModel.getBannerList()

        configurationViewModel.bannerLiveData.observe(this){
            when(it)
            {
                is ResponseData.Loading ->{

                }
                is ResponseData.Successful ->{
                    if (it.data?.banners?.size!! > 0)
                    {
                        bannerItems = it.data.banners
                        setAdBannerRecycler()
                    }
                }
                is ResponseData.Error ->{

                }
            }
        }
    }


    private fun getCategoryData() {

        categoryViewModel.getHomeCategory()

        categoryViewModel.allCategoriesLiveData.observe(this){
            when(it)
            {
                is ResponseData.Loading ->{
                  //  binding.p.visibility = VISIBLE
                }
                is ResponseData.Successful -> {
                 //   binding.progressBar.visibility = GONE
                    if (it.data?.categories?.size!! > 0)
                    {
                        println("Category Size " + it.data.categories.size)
                        categoryItems = it.data.categories
                        setCategoryRecycler()
                        dismissLottieView()

                    }


                }
                is ResponseData.Error ->{
                  //  binding.progressBar.visibility = GONE

                }
            }
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
            if (product_adapter.itemCount > 0) {

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
            product_adapter.loadStateFlow.collect {
                val state = it.refresh
                if (state is LoadState.Loading) {
                    //   binding.progressBar.visibility = View.VISIBLE
                } else {
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
        binding.animationView.visibility = GONE
    }


    private fun setupViewModels() {

        val configurationService = RetrofitHelper.getInstance().create(ConfigurationServices::class.java)
        val configurationRepository = ConfigurationRepository(configurationService)
        configurationViewModel = ViewModelProvider(
            this,
            ConfigurationViewModelFactory(configurationRepository)
        )[ConfigurationViewModel::class.java]

        val productService = RetrofitHelper.getInstance().create(ProductServices::class.java)
        val productRepository = ProductRepository(productService)
        productViewModel = ViewModelProvider(
            this,
            ProductViewModelFactory(productRepository)
        )[ProductViewModel::class.java]

        val categoryService = RetrofitHelper.getInstance().create(CategoryServices::class.java)
        val categoryRepository = CategoryRepository(categoryService)
        categoryViewModel = ViewModelProvider(
            this,
            CategoryViewModelFactory(categoryRepository)
        )[CategoryViewModel::class.java]
    }


    private fun setCategoryRecycler() {
        binding.categoryRecycler.layoutManager = GridLayoutManager(
            context,
            4
        )
        categoryAdapter = CategoryAdapter(requireContext(), categoryItems)
        binding.categoryRecycler.adapter = categoryAdapter
    }


    private fun setClickListeners() {

        binding.whatsapp.setOnClickListener {
            openWhatsApp()
        }

        binding.notification.setOnClickListener {
            val intent = Intent(requireContext(), NotificationActivity::class.java)
            startActivity(intent)
        }

        binding.viewAllProductTxt.setOnClickListener {
            val intent = Intent(requireContext(), ViewAllProducts::class.java)
            startActivity(intent)
        }

        binding.searchView.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }
        binding.viewAllCategoryTxt.setOnClickListener {
            val intent = Intent(requireContext(), CategoryListActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setAdBannerRecycler() {

        binding.adBanner.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )
        //        recyclerView.setOnFlingListener(null);
        //  if (contentList.size > 1) {
        binding.adBanner.addItemDecoration(CirclePagerIndicatorDecoration())
        // }
        binding.adBanner.setItemAnimator(DefaultItemAnimator())
        banner_adapter = BannerAdapter(requireContext(),bannerItems)
        binding.adBanner.adapter = banner_adapter
        val mHandler = Handler(Looper.getMainLooper())
        val SCROLLING_RUNNABLE: Runnable = object : Runnable {
            override fun run() {
                position++
                if (position < 4)//contentList.size)
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

    private fun openWhatsApp() {
        try {
            var whatsappUser = AppConfiguration.whatsapp_number
            whatsappUser = whatsappUser.replace("+", "").replace(" ", "")
            val sendIntent = Intent("android.intent.action.MAIN")
            sendIntent.putExtra("jid", "$whatsappUser@s.whatsapp.net")
            sendIntent.putExtra(Intent.EXTRA_TEXT, "hi")
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.setPackage("com.whatsapp")
            sendIntent.type = "text/plain"

            startActivity(sendIntent)
        }catch (e:Exception){
            Toast.makeText(context, "Please try Agaain.", Toast.LENGTH_SHORT).show()
        }
    }


/*
    private fun sendToWhatsapp() {
        //    progressDialog.dismiss();
        try{
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.setPackage("com.whatsapp")

            // Add the phone number and message to the Intent
            intent.putExtra("jid", "+919646793185"+  "@s.whatsapp.net")
            intent.putExtra(Intent.EXTRA_TEXT, "j")

            // Verify that the WhatsApp app is installed before starting the activity
//            if (intent.resolveActivity(requireContext().packageManager) != null) {
//                requireActivity().startActivity(intent)
//            } else {
//                // If WhatsApp is not installed, show a toast message
//                Toast.makeText(context, "WhatsApp is not installed on this device.", Toast.LENGTH_SHORT).show()
//            }
//        val intent = Intent()
//        intent.setPackage("com.whatsapp")
//        intent.action = Intent.ACTION_SEND_MULTIPLE
//        intent.putExtra(Intent.EXTRA_TEXT, "")
//        intent.type = "text/plain"
        requireActivity().startActivity(Intent.createChooser(intent, "share Image"))
    }catch (e:Exception){

        }
    }
*/


    private fun setProductRecycler() {
        binding.productRecyclerView.layoutManager =
            GridLayoutManager(context, 2)
        product_adapter = ProductAdapter(requireContext(), favouriteProductDao, this)
        // binding.productRecyclerView.setNestedScrollingEnabled(false);
        binding.productRecyclerView.adapter = product_adapter
    }

    override fun addToFavourite(id: Int) {

        CoroutineScope(Dispatchers.IO).launch {
            val product = getFavouriteProductFromId(id)
            if (product != null) {
                if (favouriteProductDao.isItemExist(id.toString())) {

                    favouriteProductDao.deleteFavouriteProduct(id)

                } else {
                    favouriteProductDao.addFavouriteProduct(product)

                }
                requireActivity().runOnUiThread {
                    product_adapter.notifyDataSetChanged()
                }

            }
        }
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

    private fun getFavouriteProductFromId(id: Int): FavouriteProduct? {
        for (products in productList) {
            if (products?.id == id) {
                return FavouriteProduct(
                    id,
                    products.ProductsVariants!!,
                    products.description,
                    products.image,
                    name = products.name,
                    products.out_of_stock
                )
            }
        }
        return null
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
        var product = getProductFromId(id)
        ProductVariantsSheet(requireActivity(),product!!,this).showDialog()

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
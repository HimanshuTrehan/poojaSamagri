package com.slyked.poojasamagri.products.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.WindowManager
import android.widget.RadioButton
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.slyked.admin.api.ProductServices
import com.slyked.admin.api.RetrofitHelper
import com.slyked.admin.product.model.CartProduct
import com.slyked.admin.product.model.Product
import com.slyked.admin.product.model.ProductsVariant
import com.slyked.admin.product.repository.ProductRepository
import com.slyked.admin.product.viewmodel.ProductViewModel
import com.slyked.admin.product.viewmodelfactory.CartViewModelFactory
import com.slyked.admin.product.viewmodelfactory.ProductViewModelFactory
import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.cart.CartViewModel
import com.slyked.poojasamagri.databinding.ActivityProductDetailsBinding
import com.slyked.poojasamagri.products.dao.CartProductDao
import com.slyked.poojasamagri.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class ProductDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDetailsBinding
    lateinit var productViewModel: ProductViewModel
    lateinit var cartViewModel: CartViewModel
    var productData:Product? = null
    var productVariantList:List<ProductsVariant>? = null
    var productId =0
    var variantIndex =0
    var price = ""
    var quantity = ""
    @Inject
    lateinit var cartProductDao: CartProductDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setupViewModel()
        getIntentData()
        //setProductData()


        binding.back.setOnClickListener {
            finish()
        }

        setSelectedQuantity(cartViewModel.getSelectedQuantity().toString())


        binding.addQuantity.setOnClickListener {
            cartViewModel.addSelectedQuantity()
            setSelectedQuantity(cartViewModel.getSelectedQuantity().toString())

        }
        binding.minusQuantity.setOnClickListener {
            cartViewModel.deleteSelectedQuantity()
            setSelectedQuantity(cartViewModel.getSelectedQuantity().toString())

        }
        binding.addToCartBtn.setOnClickListener {
            productData?.let {
                val selectedQuantity = cartViewModel.getSelectedQuantity()
                val cartItem = CartProduct(it.id,
                    productVariantList?.get(variantIndex)!!,it.description,it.image,it.name,selectedQuantity)
                CoroutineScope(Dispatchers.IO).launch {
                    if (!cartProductDao.isItemExist(it.id.toString())) {
                        val response = async { cartProductDao.addToCartProduct(cartItem) }
                        println("addCart "+response.await())
                        if (response.await() > 0) {
                            runOnUiThread {
                                updateAddToCart()
                            }
                        }

                    }else{
                        val response = async { cartProductDao.deleteCartProduct(it.id) }
                        if (response.await() == 1) {
                            runOnUiThread {
                                removeFromCart()
                            }
                        }
                    }
                }
            }

        }


    }



    private fun setSelectedQuantity(quantity:String){
        binding.selectedQuantity.text = quantity
    }
   private fun removeFromCart(){
        binding.addToCartBtn.text = "Add To Cart"
        binding.addToCartBtn.setBackgroundColor(resources.getColor(R.color.primary))
    }

    private fun updateAddToCart(){
        binding.addToCartBtn.text = "Remove From Cart"
        binding.addToCartBtn.setBackgroundColor(Color.RED)
    }

    private fun setProductData() {
        binding.animationView.cancelAnimation()
        binding.animationView.visibility = GONE
        productData?.let {
            binding.description.text =    it.description
            binding.productName.text = it.name
            binding.productPrice.text ="\u209B "+ it.ProductsVariants?.get(0)?.price
            binding.quantityTxt.text = it.ProductsVariants?.get(0)?.qty
            try {

                val imageUrl = Constants.IMAGE_BASE_URL+it.image
                Glide.with(applicationContext).asBitmap().load(imageUrl).into(object : CustomTarget<Bitmap?>() {

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                        binding.productImage.setImageBitmap(resource)
                    }
                })

            }catch (e:java.lang.Exception)
            {
                println(e)
            }

            productVariantList = it.ProductsVariants
            if (productVariantList !=null && productVariantList!!.size>0){
                setPriceAndQuantity(0)

                setRadioButton()
            }
            CoroutineScope(Dispatchers.IO).launch {
                if (cartProductDao.isItemExist(it.id.toString())){
                    runOnUiThread {
                        updateAddToCart()
                    }
                }

            }


        }



    }

    private fun setPriceAndQuantity(index: Int) {
        price = productVariantList!![index].price
        quantity = productVariantList!![index].qty

        binding.productPrice.text ="\u20B9 "+ price
        binding.quantityTxt.text = quantity

    }

    private fun getIntentData() {
        productId = intent.getIntExtra("productId",0)
        if (productId !=0) {
            CoroutineScope(Dispatchers.IO).launch {
                val response = async { productViewModel.getProductsById(productId) }
                if (response.await().isSuccessful && response.await().body() != null) {
                    productData = response.await().body()!!.data?.product
                    runOnUiThread{
                        setProductData()
                    }
                    //setRadioButton()
                }
            }
        }

    }

    private fun setupViewModel() {
        val service = RetrofitHelper.getInstance().create(ProductServices::class.java)

        val repository = ProductRepository(service)

        productViewModel = ViewModelProvider(this, ProductViewModelFactory(repository)).get(
            ProductViewModel::class.java)

        cartViewModel =  ViewModelProvider(this, CartViewModelFactory(cartProductDao)).get(
            CartViewModel::class.java)
    }

    private fun setRadioButton() {

        for (variants in productVariantList!!) {
            val radioButton = RadioButton(this)
            radioButton.text ="Quantity: "+ variants.qty +"\n Price: "+ variants.price
            // Set other properties of the radio button if needed
            binding.radioGroup.addView(radioButton)
        }
        binding. radioGroup.check(binding.radioGroup.getChildAt(variantIndex).id);

        // Set a listener to handle radio button selection
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            variantIndex = checkedId - 1
            setPriceAndQuantity(variantIndex)

        }
    }


}
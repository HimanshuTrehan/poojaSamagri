package com.slyked.poojasamagri.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.slyked.admin.api.CategoryServices
import com.slyked.admin.api.ProductServices
import com.slyked.admin.api.ResponseData
import com.slyked.admin.api.RetrofitHelper
import com.slyked.admin.category.model.CategoryListData
import com.slyked.admin.category.repository.CategoryRepository
import com.slyked.admin.category.viewmodel.CategoryViewModel
import com.slyked.admin.category.viewmodelfactory.CategoryViewModelFactory
import com.slyked.admin.product.model.AllProductModel
import com.slyked.admin.product.repository.ProductRepository
import com.slyked.admin.product.viewmodel.ProductViewModel
import com.slyked.admin.product.viewmodelfactory.ProductViewModelFactory
import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.databinding.ActivityMainBinding
import com.slyked.poojasamagri.fragments.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var productViewModel:ProductViewModel
    lateinit var categoryViewModel:CategoryViewModel
     var productLiveData: LiveData<ResponseData<AllProductModel>>? = null
     var categoryLiveData: LiveData<ResponseData<CategoryListData>>? =null
    var backStateName: String = ""
    var flag = true;
    var integerQueue = ArrayDeque<Int>(3)


    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        // Save custom values into the bundle
        println("onSaveInstanceState onCreate")

        savedInstanceState.putInt("selected_index", 3)
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState)
        println("onRestoreInstanceState onCreate")

        // Restore state members from saved instance
       val someIntValue = savedInstanceState.getInt("selected_index")
        if (someIntValue == 3) {
            println("onRestoreInstanceState 3")
            integerQueue.addFirst(R.id.home)
            loadFragment(ProfileFragment())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        println("MainActivity onCreate")

        createViewModels()
        binding.bottomNav.menu.getItem(0).isChecked = true

        integerQueue.addFirst(R.id.home)
        loadFragment(HomeFragment())
       binding.bottomNav.setOnItemSelectedListener {
           if (integerQueue.contains(it.itemId))
           {
               if (it.itemId == R.id.home)
               {
                   if (integerQueue.size!= 1)
                   {
                        if (flag){
                            integerQueue.addFirst(R.id.home)
                            flag = false
                        }
                   }
               }
               integerQueue.remove(it.itemId)
           }
           integerQueue.add(it.itemId)
           loadFragment(getFragment(it.itemId))
           false

        }
    }

    private fun getFragment(id:Int):Fragment
    {
        when (id) {
            R.id.home -> {
                binding.bottomNav.menu.getItem(0).isChecked = true
                return HomeFragment()

            }
            R.id.fav -> {
                binding.bottomNav.menu.getItem(1).isChecked = true
                return FavouriteFragment()
            }
            R.id.cart -> {
                binding.bottomNav.menu.getItem(2).isChecked = true
                return CartFragment()
            }
            R.id.profile -> {
                binding.bottomNav.menu.getItem(3).isChecked = true
                return ProfileFragment()
            }
            else -> {
                return HomeFragment()
            }
        }
    }



    private fun createViewModels() {
        val productService = RetrofitHelper.getInstance().create(ProductServices::class.java)
        val productRepository = ProductRepository(productService)
        productViewModel = ViewModelProvider(this,
            ProductViewModelFactory(productRepository)
        )[ProductViewModel::class.java]

        val categoryService = RetrofitHelper.getInstance().create(CategoryServices::class.java)
        val categoryRepository = CategoryRepository(categoryService)
        categoryViewModel = ViewModelProvider(this,
            CategoryViewModelFactory(categoryRepository)
        )[CategoryViewModel::class.java]
    }


    override fun onBackPressed() {
        super.onBackPressed()
        if(integerQueue.size == 1){
            finish()
            return
        }
        if (integerQueue.isNotEmpty()){
            integerQueue.removeLast()
            loadFragment(getFragment(integerQueue.last()))
        }else{
            finish()
        }


    }

    private  fun loadFragment(fragment: Fragment){
         backStateName = fragment.javaClass.name
        println()
        var exists = isFragmentInBackstack(fragmentManager = supportFragmentManager,backStateName)
        if (exists) {
            // Fragment exists, go back to that fragment
            //// you can also use POP_BACK_STACK_INCLUSIVE flag, depending on flow
            supportFragmentManager.popBackStackImmediate(fragment::class.java.name, 0)
        } else {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,fragment)
            transaction.addToBackStack(backStateName)
            transaction.commit()
        }

    }

    fun isFragmentInBackstack(fragmentManager: FragmentManager, fragmentTagName: String): Boolean {
        for (entry in 0 until fragmentManager.backStackEntryCount) {
            if (fragmentTagName == fragmentManager.getBackStackEntryAt(entry).name) {
                return true
            }
        }
        return false
    }
}
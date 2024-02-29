package com.slyked.poojasamagri.category.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import com.slyked.admin.api.CategoryServices
import com.slyked.admin.api.ResponseData
import com.slyked.admin.api.RetrofitHelper
import com.slyked.admin.api.SubCategoryServices
import com.slyked.admin.category.model.Category
import com.slyked.admin.category.repository.CategoryRepository
import com.slyked.admin.category.viewmodel.CategoryViewModel
import com.slyked.admin.category.viewmodelfactory.CategoryViewModelFactory
import com.slyked.admin.subcategory.model.SubCategory
import com.slyked.admin.subcategory.repository.SubCategoryRepository
import com.slyked.admin.subcategory.viewmodel.SubCategoryViewModel
import com.slyked.admin.subcategory.viewmodelfactory.SubCategoryViewModelFactory
import com.slyked.poojasamagri.category.adapter.CategoryListAdapter
import com.slyked.poojasamagri.databinding.ActivityCategoryListBinding
import com.slyked.poojasamagri.products.ui.ViewAllProducts
import com.slyked.poojasamagri.subcategory.ui.SubCategoryActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CategoryListActivity : AppCompatActivity(), CategoryListAdapter.ItemOperationListener {
    lateinit var binding: ActivityCategoryListBinding
    lateinit var categoryListAdapter: CategoryListAdapter
    lateinit var categoryViewModel:CategoryViewModel
   lateinit var subCategoryViewModel: SubCategoryViewModel

    lateinit var categoryItems: List<Category>
    lateinit var subCategoryItems: List<SubCategory>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        binding.back.setOnClickListener {
            finish()
        }
        setCategoryRecycler()
        getData()
        //setData()


    }

    private fun getData() {
        categoryItems = arrayListOf()
        subCategoryItems  = arrayListOf()
        lifecycleScope.launch {
            categoryViewModel.getCategory.collectLatest {
                categoryListAdapter.submitData(lifecycle,it)
            }


        }

        lifecycleScope.launch{
            categoryListAdapter.loadStateFlow.collect{
                val state = it.refresh

                if(state is LoadState.Loading){
                    binding.progressBar.visibility = View.VISIBLE
                }else{
                    binding.progressBar.visibility = View.GONE

                }

            }
        }

      //  categoryViewModel.getCategory(1)

    }


    private fun setupViewModel() {
        val service = RetrofitHelper.getInstance().create(CategoryServices::class.java)

        val repository = CategoryRepository(service)

        categoryViewModel = ViewModelProvider(this, CategoryViewModelFactory(repository)).get(
            CategoryViewModel::class.java
        )
        val subCategoryService = RetrofitHelper.getInstance().create(SubCategoryServices::class.java)

        val subCategoryRepository = SubCategoryRepository(subCategoryService)

        subCategoryViewModel = ViewModelProvider(this, SubCategoryViewModelFactory(subCategoryRepository)).get(
            SubCategoryViewModel::class.java
        )

    }

/*
    private fun setData() {


        categoryViewModel.allCategoriesLiveData.observe(this){
            when(it)
            {
                is ResponseData.Loading ->{
                    binding.progressBar.visibility = View.VISIBLE
                    binding.notFound.visibility = View.GONE

                }
                is ResponseData.Successful -> {
                    binding.progressBar.visibility = View.GONE
                    if (it.data?.categories?.size!! > 0)
                    {
                        println("Category Size " + it.data.categories.size)
                        categoryItems = it.data.categories

                        setCategoryRecycler()
                    }else{
                        binding.CategoriesListRecyclerView.visibility = View.GONE
                        binding.notFound.visibility = View.VISIBLE
                    }


                }
                is ResponseData.Error ->{
                    binding.progressBar.visibility = View.GONE
                    binding.notFound.visibility = View.VISIBLE
                }
            }
        }


    }
*/



    private fun setCategoryRecycler() {
        binding.CategoriesListRecyclerView.layoutManager = GridLayoutManager(applicationContext,4)
        categoryListAdapter = CategoryListAdapter(applicationContext,this)
        binding.CategoriesListRecyclerView.adapter = categoryListAdapter

    }

    override fun onClicked(id: Int) {
        binding.progressBar.visibility = VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
        val response = async{   subCategoryViewModel.checkSubCategoryById(id)}

//            if (response.await() != null && response.await()!!.isNotEmpty()){
//                runOnUiThread {
//                    binding.progressBar.visibility = GONE
//                    openSubcategory(id)
//                }
//
//            }else{
                runOnUiThread{
                    binding.progressBar.visibility = GONE
                    openViewProductByCategoryId(id)
                }

         //   }
        }


    }

    private fun openViewProductByCategoryId(id:Int) {
        val intent = Intent(applicationContext, ViewAllProducts::class.java)
        intent.putExtra("categoryId",id)
        startActivity(intent)
    }

    private fun openSubcategory(id:Int) {
        val intent = Intent(applicationContext, SubCategoryActivity::class.java)
        intent.putExtra("categoryId",id)
        startActivity(intent)

    }
}
package com.slyked.poojasamagri.subcategory.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.slyked.admin.api.ResponseData
import com.slyked.admin.api.RetrofitHelper
import com.slyked.admin.api.SubCategoryServices
import com.slyked.admin.subcategory.model.SubCategory
import com.slyked.admin.subcategory.repository.SubCategoryRepository
import com.slyked.admin.subcategory.viewmodel.SubCategoryViewModel
import com.slyked.admin.subcategory.viewmodelfactory.SubCategoryViewModelFactory
import com.slyked.poojasamagri.subcategory.adapter.SubCategoryAdapter
import com.slyked.poojasamagri.databinding.ActivityAddSubCategoryBinding
import com.slyked.poojasamagri.products.ui.ViewAllProducts


class SubCategoryActivity : AppCompatActivity(),
    SubCategoryAdapter.ItemOperationListener {
    lateinit var binding: ActivityAddSubCategoryBinding
    lateinit var adapter: SubCategoryAdapter
    var subCategoryList: List<SubCategory>? = arrayListOf()
    lateinit var viewModel: SubCategoryViewModel
    var categoryId: Int = 0
    var subCategoryId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSubCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createViewModel()
        getCategoryId()
        setClickListeners()

        viewModel.subCategoryLiveData.observe(this) {
            when (it) {
                is ResponseData.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.notFound.visibility = View.GONE
                }
                is ResponseData.Successful -> {
                    binding.progressBar.visibility = View.GONE
                    if (it.data?.subcategories?.size!! > 0) {
                        subCategoryList = it.data.subcategories
                        setCategoryRecycler()
                    } else {
                        binding.addSubCategoriesRecycler.visibility = View.GONE
                        binding.notFound.visibility = View.VISIBLE

                    }
                }
                is ResponseData.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.addSubCategoriesRecycler.visibility = View.GONE
                    binding.notFound.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getCategoryId() {
        categoryId = intent.getIntExtra("categoryId", 0)
        if (categoryId != 0) {
            viewModel.getSubCategoryById(categoryId)
        }
    }

    override fun onResume() {
        super.onResume()
        if (categoryId != 0) {
            viewModel.getSubCategoryById(categoryId)
        }
    }

    private fun getSubCategoryList() {

    }

    private fun setClickListeners() {
        binding.back.setOnClickListener {
            finish()
        }

    }

    private fun createViewModel() {
        val service = RetrofitHelper.getInstance().create(SubCategoryServices::class.java)
        val repository = SubCategoryRepository(service)
        viewModel = ViewModelProvider(this, SubCategoryViewModelFactory(repository)).get(
            SubCategoryViewModel::class.java
        )
    }



    private fun setCategoryRecycler() {
        binding.addSubCategoriesRecycler.layoutManager = LinearLayoutManager(
            applicationContext,
            RecyclerView.VERTICAL, false
        )
        binding.addSubCategoriesRecycler.addItemDecoration(
            DividerItemDecoration(
                baseContext,
                RecyclerView.VERTICAL
            )
        )
        adapter = SubCategoryAdapter(applicationContext, subCategoryList!!, this)
        binding.addSubCategoriesRecycler.adapter = adapter

    }

    override fun onClicked(id: Int) {
        openViewProductBySubCategoryId(id)
    }
    private fun openViewProductBySubCategoryId(id:Int) {
        val intent = Intent(applicationContext, ViewAllProducts::class.java)
        intent.putExtra("categoryId",id)
        startActivity(intent)
    }

}
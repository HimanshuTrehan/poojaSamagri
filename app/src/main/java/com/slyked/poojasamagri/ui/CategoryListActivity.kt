package com.slyked.poojasamagri.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.slyked.poojasamagri.adapter.CategoryListAdapter
import com.slyked.poojasamagri.databinding.ActivityCategoryListBinding
import com.slyked.poojasamagri.model.SubCategoryModel
import com.slyked.poojasamagri.utils.CommonMethods

class CategoryListActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryListBinding
    lateinit var categoryListAdapter: CategoryListAdapter
    lateinit var subCategoryList: ArrayList<SubCategoryModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.back.setOnClickListener {
            finish()
        }
        setData()
        setCategoryRecycler()

    }

    private fun setData() {
        subCategoryList  = arrayListOf()
        val jsonFileString = applicationContext?.let {

            CommonMethods.getJsonDataFromFile(it, "category.json")
        }
        val gson = Gson()

        val listCoverType = object : TypeToken<List<SubCategoryModel>>() {}.type

        var coverD: List<SubCategoryModel> = gson.fromJson(jsonFileString, listCoverType)

        coverD.forEachIndexed { idx, data ->

            subCategoryList.add(data)

        }
    }

    private fun setCategoryRecycler() {
        binding.CategoriesListRecyclerView.layoutManager = LinearLayoutManager(applicationContext,VERTICAL,false)
        categoryListAdapter = CategoryListAdapter(applicationContext,subCategoryList)
        binding.CategoriesListRecyclerView.adapter = categoryListAdapter

    }
}
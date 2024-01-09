package com.slyked.poojasamagri.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.slyked.poojasamagri.adapter.ProductAdapter
import com.slyked.poojasamagri.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding
    lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setProductRecycler()
    }


    private fun setProductRecycler() {
        binding.productRecyclerView.layoutManager = GridLayoutManager(applicationContext,2)
        productAdapter = ProductAdapter(applicationContext)
        binding.productRecyclerView.adapter = productAdapter
    }
}
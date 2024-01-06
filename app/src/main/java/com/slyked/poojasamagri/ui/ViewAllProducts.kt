package com.slyked.poojasamagri.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.adapter.ProductAdapter
import com.slyked.poojasamagri.databinding.ActivityViewAllProductsBinding

class ViewAllProducts : AppCompatActivity() {

    lateinit var binding:ActivityViewAllProductsBinding
    lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAllProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    setProductRecycler()

    }

    private fun setProductRecycler() {
        binding.productRecyclerView.layoutManager = GridLayoutManager(applicationContext,2)
        productAdapter = ProductAdapter(applicationContext)
        binding.productRecyclerView.adapter = productAdapter
    }
}
package com.slyked.poojasamagri.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.adapter.BannerAdapter
import com.slyked.poojasamagri.adapter.CategoryAdapter
import com.slyked.poojasamagri.adapter.ProductAdapter
import com.slyked.poojasamagri.databinding.ActivityMainBinding
import com.slyked.poojasamagri.databinding.FragmentHomeBinding
import com.slyked.poojasamagri.ui.ViewAllProducts


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var product_adapter: ProductAdapter
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var banner_adapter: BannerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        setCategoryRecycler()
        setAdBannerRecycler()
        setProductRecycler()
        setClickListeners()
        return binding.root;

    }

    private fun setCategoryRecycler() {
        binding.categoryRecycler.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter = CategoryAdapter(requireContext())
        binding.categoryRecycler.adapter = categoryAdapter
    }

    private fun setClickListeners() {
        binding.viewAllProductTxt.setOnClickListener {
            val intent = Intent(requireContext(),ViewAllProducts::class.java)
            startActivity(intent)
        }
    }

    private fun setAdBannerRecycler() {

        binding.adBanner.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
        banner_adapter = BannerAdapter(requireContext())
        binding.adBanner.adapter = banner_adapter
    }

    private fun setProductRecycler() {
        binding.productRecyclerView.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
        product_adapter = ProductAdapter(requireContext())
        binding.productRecyclerView.adapter = product_adapter
    }


}
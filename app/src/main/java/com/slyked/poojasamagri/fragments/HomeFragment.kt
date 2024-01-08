package com.slyked.poojasamagri.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.slyked.poojasamagri.adapter.BannerAdapter
import com.slyked.poojasamagri.adapter.CategoryAdapter
import com.slyked.poojasamagri.adapter.ProductAdapter
import com.slyked.poojasamagri.databinding.FragmentHomeBinding
import com.slyked.poojasamagri.ui.ViewAllProducts
import com.slyked.poojasamagri.utils.CirclePagerIndicatorDecoration


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var product_adapter: ProductAdapter
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var banner_adapter: BannerAdapter
    var position = -1
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
        binding.productRecyclerView.layoutManager = GridLayoutManager(context,2)
        product_adapter = ProductAdapter(requireContext())
       // binding.productRecyclerView.setNestedScrollingEnabled(false);
        binding.productRecyclerView.adapter = product_adapter
    }


}
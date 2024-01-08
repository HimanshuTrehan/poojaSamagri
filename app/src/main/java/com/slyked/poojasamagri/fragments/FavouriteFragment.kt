package com.slyked.poojasamagri.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.slyked.poojasamagri.adapter.CategoryListAdapter
import com.slyked.poojasamagri.adapter.ProductAdapter
import com.slyked.poojasamagri.databinding.FragmentCategoryBinding


class FavouriteFragment : Fragment() {

    lateinit var binding: FragmentCategoryBinding
    lateinit var favouriteListAdapter: ProductAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = FragmentCategoryBinding.inflate(inflater,container,false)

        setCategoryRecycler()

        return binding.root
    }

    private fun setCategoryRecycler() {
        binding.favRecyclerView.layoutManager = GridLayoutManager(context,2)
        favouriteListAdapter = ProductAdapter(requireContext())
        binding.favRecyclerView.adapter = favouriteListAdapter

    }


}
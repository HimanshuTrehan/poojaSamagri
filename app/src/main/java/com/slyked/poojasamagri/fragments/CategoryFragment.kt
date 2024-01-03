package com.slyked.poojasamagri.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.adapter.CategoryAdapter
import com.slyked.poojasamagri.databinding.FragmentCategoryBinding
import com.slyked.poojasamagri.databinding.FragmentHomeBinding


class CategoryFragment : Fragment() {

    lateinit var binding: FragmentCategoryBinding
    lateinit var categoryAdapter: CategoryAdapter
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
        binding.categoriesRecyclerView.layoutManager = GridLayoutManager(context,2)
        categoryAdapter = CategoryAdapter(requireContext())
        binding.categoriesRecyclerView.adapter = categoryAdapter

    }


}
package com.slyked.poojasamagri.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.adapter.OrderAdapter
import com.slyked.poojasamagri.databinding.FragmentCompletedOrderBinding
import com.slyked.poojasamagri.databinding.FragmentCurrentOrderBinding

class CurrentOrderFragment : Fragment() {

    lateinit var binding: FragmentCurrentOrderBinding
    lateinit var orderAdapter: OrderAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCurrentOrderBinding.inflate(layoutInflater,container,false)

        setCategoryRecycler()
        return binding.root
    }

    private fun setCategoryRecycler() {

        binding.orderRecycler.layoutManager = LinearLayoutManager(context,
            RecyclerView.VERTICAL,false)
        orderAdapter = OrderAdapter(requireContext())
        binding.orderRecycler.adapter = orderAdapter

    }


}
package com.slyked.poojasamagri.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.slyked.poojasamagri.adapter.OrderAdapter
import com.slyked.poojasamagri.databinding.FragmentCompletedOrderBinding


class CompletedOrderFragment : Fragment() {

    lateinit var binding: FragmentCompletedOrderBinding
    lateinit var orderAdapter: OrderAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCompletedOrderBinding.inflate(layoutInflater,container,false)

        setCategoryRecycler()
        return binding.root
    }

    private fun setCategoryRecycler() {

        binding.orderRecycler.layoutManager = LinearLayoutManager(context,VERTICAL,false)
        orderAdapter = OrderAdapter(requireContext())
        binding.orderRecycler.adapter = orderAdapter

    }


}
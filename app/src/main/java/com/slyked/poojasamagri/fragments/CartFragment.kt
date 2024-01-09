package com.slyked.poojasamagri.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.adapter.CartAdapter
import com.slyked.poojasamagri.databinding.FragmentCartBinding
import com.slyked.poojasamagri.databinding.FragmentHomeBinding
import com.slyked.poojasamagri.ui.OrderSummary


class CartFragment : Fragment() {
    lateinit var binding: FragmentCartBinding
    lateinit var adapter: CartAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater,container,false);
        setupRecycler()
        binding.checkoutBtn.setOnClickListener {
            val intent = Intent(requireContext(),OrderSummary::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    private fun setupRecycler() {
        binding.cartRecycler.layoutManager = LinearLayoutManager(requireContext(),VERTICAL,false)
        adapter = CartAdapter(requireContext())
        binding.cartRecycler.adapter = adapter
    }


}
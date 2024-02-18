package com.slyked.poojasamagri.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slyked.poojasamagri.adapter.OrderSummaryItemAdapter
import com.slyked.poojasamagri.databinding.ActivityOrderSummaryBinding

class OrderSummary : AppCompatActivity() {

    lateinit var binding: ActivityOrderSummaryBinding
    lateinit var adapter: OrderSummaryItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderSummaryBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.back.setOnClickListener {
            finish()
        }

        binding.checkout.setOnClickListener {
            val intent = Intent(applicationContext,OrderSuccessActivity::class.java)
            startActivity(intent)
        }

        binding.editDeliveryAddressTextView.setOnClickListener {
            val intent = Intent(applicationContext,AddressDetailsActivity::class.java)
            startActivity(intent)
        }
        setItemsRecycler()
    }

    private fun setItemsRecycler() {
        binding.itemsRecyclerView.layoutManager = LinearLayoutManager(applicationContext,
            RecyclerView.VERTICAL,false)
        adapter = OrderSummaryItemAdapter(applicationContext)
        binding.itemsRecyclerView.adapter = adapter

    }
}
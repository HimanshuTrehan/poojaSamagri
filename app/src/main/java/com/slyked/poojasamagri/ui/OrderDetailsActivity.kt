package com.slyked.poojasamagri.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.slyked.poojasamagri.adapter.OrderViewPager
import com.slyked.poojasamagri.databinding.ActivityOrderDetailsBinding

class OrderDetailsActivity : AppCompatActivity() {
    lateinit var orderViewPager: OrderViewPager
    lateinit var binding : ActivityOrderDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)


        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
        }

        orderViewPager = OrderViewPager(this)
        binding.viewPager.adapter = orderViewPager
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.viewPager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }
}
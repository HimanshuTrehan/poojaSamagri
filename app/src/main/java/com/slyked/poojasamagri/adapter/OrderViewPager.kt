package com.slyked.poojasamagri.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.slyked.poojasamagri.fragments.CompletedOrderFragment
import com.slyked.poojasamagri.fragments.CurrentOrderFragment
import com.slyked.poojasamagri.ui.OrderDetailsActivity

class OrderViewPager(activity: OrderDetailsActivity): FragmentStateAdapter(activity) {


    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
             0 -> CurrentOrderFragment()
             1  -> CompletedOrderFragment()

            else -> {
                CurrentOrderFragment()
            }
        }
    }


}
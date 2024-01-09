package com.slyked.poojasamagri.fragments

import android.content.Intent
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.databinding.FragmentProfileBinding
import com.slyked.poojasamagri.ui.OrderDetailsActivity


class ProfileFragment : Fragment() {

    lateinit var binding:FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        animateView()
        binding.ordersLayout.setOnClickListener {
            val intent = Intent(requireContext(),OrderDetailsActivity::class.java)
            startActivity(intent)
        }
        return binding.root



    }

    private fun animateView() {
        binding.expandIcon.setOnClickListener {
            // If the CardView is already expanded, set its visibility
            // to gone and change the expand less icon to expand more.
            if (binding.hiddenProfileLayout.getVisibility() === View.VISIBLE) {
                // The transition of the hiddenView is carried out by the TransitionManager class.
                // Here we use an object of the AutoTransition Class to create a default transition
                TransitionManager.beginDelayedTransition(binding.editProfileLayout, AutoTransition())
                binding.hiddenProfileLayout.setVisibility(View.GONE)
                binding.expandIcon.setImageResource(R.drawable.ic_forward)
            } else {
                TransitionManager.beginDelayedTransition(binding.editProfileLayout, AutoTransition())
                binding.hiddenProfileLayout.setVisibility(View.VISIBLE)
                binding.expandIcon.setImageResource(R.drawable.ic_down)
            }
        }
    }


}
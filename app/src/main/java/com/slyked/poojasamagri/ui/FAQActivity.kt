package com.slyked.poojasamagri.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.slyked.poojasamagri.databinding.ActivityFaqactivityBinding

class FAQActivity : AppCompatActivity() {
    lateinit var binding: ActivityFaqactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
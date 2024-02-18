package com.slyked.poojasamagri.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.slyked.poojasamagri.databinding.ActivityAboutUsBinding

class AboutUsActivity : AppCompatActivity() {
    lateinit var binding:ActivityAboutUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
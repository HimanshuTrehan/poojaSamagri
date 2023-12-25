package com.slyked.poojasamagri.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.slyked.poojasamagri.databinding.ActivityOtpscreenBinding

class OTPVerificationScreen : AppCompatActivity() {
    lateinit var binding:ActivityOtpscreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.verifyBtn.setOnClickListener {
            val intent = Intent(this,LocationDetectionScreen::class.java)
            startActivity(intent)
        }



    }
}
package com.slyked.poojasamagri.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.slyked.poojasamagri.databinding.ActivityLoginBinding

class LoginScreen:AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            val intent = Intent(this,OTPVerificationScreen::class.java)
            startActivity(intent)
        }
    }
}
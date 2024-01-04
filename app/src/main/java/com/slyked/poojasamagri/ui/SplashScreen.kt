package com.slyked.poojasamagri.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.databinding.ActivitySpalshBinding

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    lateinit var binding: ActivitySpalshBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpalshBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToHome()
    }

    private fun navigateToHome() {

        val animation = AnimationUtils.loadAnimation(this,R.anim.zoom_in)
        binding.splashImg.startAnimation(animation)

        Handler().postDelayed(
            {
                val intent = Intent(this, LoginScreen::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            },3100

        )
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!binding.splashImg.animation.hasEnded()) {
            binding.splashImg.clearAnimation()
        }
    }
}
package com.slyked.poojasamagri.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.databinding.ActivitySpalshBinding
import com.slyked.poojasamagri.utils.Constants
import com.slyked.poojasamagri.utils.manager.SharedPreferencesManager

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    lateinit var binding: ActivitySpalshBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpalshBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startAnimation()
        val userName = SharedPreferencesManager.getKey(applicationContext,Constants.USER_NAME)
        if (userName !=null && userName.isNotBlank()) {
            navigateToHomeScreen()
        }else{
            navigateToLoginScreen()
        }
    }
  private fun startAnimation()
  {
      val animation = AnimationUtils.loadAnimation(this,R.anim.zoom_in)
      binding.splashImg.startAnimation(animation)
  }
    private fun navigateToHomeScreen() {
        Handler().postDelayed(
            {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            },3100

        )
    }
    private fun navigateToLoginScreen() {
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
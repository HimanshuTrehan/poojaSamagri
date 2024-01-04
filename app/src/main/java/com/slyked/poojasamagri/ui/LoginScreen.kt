package com.slyked.poojasamagri.ui

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.databinding.ActivityLoginBinding
import com.slyked.poojasamagri.utils.Constants
import com.slyked.poojasamagri.utils.Validations
import com.slyked.poojasamagri.utils.manager.SharedPreferencesManager

class LoginScreen:AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    var phoneNumber: String = ""
    var name: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAnimation()

        binding.loginBtn.setOnClickListener {
            name = binding.nameEditText.text.toString()
            phoneNumber = binding.mobileEditText.text.toString()

            if(checkDetailsValid(name,phoneNumber)) {
                SharedPreferencesManager.putKey(applicationContext,Constants.USER_NAME,name)
                SharedPreferencesManager.putKey(applicationContext,Constants.USER_PHONE_NUMBER,phoneNumber)
                val intent = Intent(this, OTPVerificationScreen::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setupAnimation() {
        val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.bottom_up)

        binding.loginDetailsCardView.startAnimation(animation)
    }

    private fun checkDetailsValid(name:String,phoneNumber:String): Boolean {
        var result = false
       if(name.isBlank())
       {
           Toast.makeText(applicationContext,Constants.EMPTY_NAME,Toast.LENGTH_SHORT).show()
       }
       else if(phoneNumber.isBlank())
        {
            Toast.makeText(applicationContext,Constants.EMPTY_PHONE_NUMBER,Toast.LENGTH_SHORT).show()
        }
       else if (!Validations.phoneNumberValidator(phoneNumber))
        {
            Toast.makeText(applicationContext,Constants.INVALID_PHONE_NUMBER,Toast.LENGTH_SHORT).show()

        }else{
            result = true
        }

        return result

    }
}
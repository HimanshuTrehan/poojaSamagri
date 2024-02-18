package com.slyked.poojasamagri

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.slyked.poojasamagri.utils.manager.SharedPreferencesManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PoojaApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        val isDarkMode = SharedPreferencesManager.getKey(applicationContext,"isDarkMode")

        if (isDarkMode.equals("true")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }




}
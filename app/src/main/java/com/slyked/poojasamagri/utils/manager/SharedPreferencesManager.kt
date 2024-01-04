package com.slyked.poojasamagri.utils.manager

import android.content.Context
import android.content.SharedPreferences
import com.slyked.poojasamagri.utils.Constants

class SharedPreferencesManager {


    companion object {
        lateinit var sharedPreferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
    fun putKey(context: Context, Key: String?, Value: String?) {
        sharedPreferences =
            context.getSharedPreferences(Constants.POOJA_APP_CACHE_DIRECTORY, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.putString(Key, Value)
        editor.apply()
    }

    fun getKey(contextGetKey: Context, Key: String?): String? {
        sharedPreferences =
            contextGetKey.getSharedPreferences(
                Constants.POOJA_APP_CACHE_DIRECTORY,
                Context.MODE_PRIVATE
            )
        return sharedPreferences.getString(Key, "")
    }


    fun clearSharedPreferences(context: Context) {
        sharedPreferences =
            context.getSharedPreferences(Constants.POOJA_APP_CACHE_DIRECTORY, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }
}

}
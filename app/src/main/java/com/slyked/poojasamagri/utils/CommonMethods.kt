package com.slyked.poojasamagri.utils

import android.content.Context
import java.io.IOException

class CommonMethods {

    companion object {

        fun getJsonDataFromFile(context: Context, fileName: String): String? {

            val jsonString: String
            try {
                jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                return null
            }
            return jsonString
        }
    }
}
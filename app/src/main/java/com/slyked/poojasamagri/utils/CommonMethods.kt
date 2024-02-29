package com.slyked.poojasamagri.utils

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.widget.Toast
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

        fun toastMessage(context: Context,message:String?)
        {
            message?.let {
                Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
            }
        }
        fun getSpannedText(text: String): Spanned? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(text)
            }
        }
    }
}
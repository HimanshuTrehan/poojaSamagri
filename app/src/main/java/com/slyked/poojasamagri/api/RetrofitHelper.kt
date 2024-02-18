package com.slyked.admin.api

import android.annotation.SuppressLint
import com.slyked.poojasamagri.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {

    private var okHttpClient1: OkHttpClient? = null

    fun getInstance(): Retrofit
    {
        initOkHttp()
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).
            client(okHttpClient1)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }


    private fun initOkHttp()
    {
        val httpClient = OkHttpClient().newBuilder()
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .addHeader("Accept", "application/json")
                .header("Content-Type", "application/json")

            val request = requestBuilder.build()
            chain.proceed(request)
        }
        okHttpClient1 = httpClient.build()
    }

}
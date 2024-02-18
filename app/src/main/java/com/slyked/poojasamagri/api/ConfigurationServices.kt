package com.slyked.admin.api


import com.slyked.admin.configuration.model.OffersModel
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query


interface ConfigurationServices {


    @GET("/api/promo-codes?pageSize=30")
    suspend fun getOffers(@Query(value = "page") page:Int):Response<OffersModel>




}
package com.slyked.admin.api


import com.slyked.admin.configuration.model.AppConfigurationModel
import com.slyked.admin.configuration.model.BannerModel
import com.slyked.admin.configuration.model.OffersModel
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query


interface ConfigurationServices {


    @GET("/api/promo-codes?pageSize=30")
    suspend fun getOffers(@Query(value = "page") page:Int):Response<OffersModel>


    @GET("/api/configurations")
     suspend fun getConfigurations():Response<AppConfigurationModel>

    @GET("/api/banners?page=1&pageSize=20")
    suspend fun getBanners():Response<BannerModel>


}
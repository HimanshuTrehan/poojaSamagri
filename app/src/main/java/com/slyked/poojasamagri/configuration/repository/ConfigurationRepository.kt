package com.slyked.admin.configuration.repository

import com.slyked.admin.api.ConfigurationServices
import com.slyked.admin.configuration.model.OffersModel
import com.slyked.admin.configuration.model.PromoCode
import com.slyked.admin.models.CommonResponse
import retrofit2.Response

class ConfigurationRepository(private val services: ConfigurationServices) {


   suspend fun getOfferList(page:Int):Response<OffersModel>
    {
        return services.getOffers(page)
    }





}
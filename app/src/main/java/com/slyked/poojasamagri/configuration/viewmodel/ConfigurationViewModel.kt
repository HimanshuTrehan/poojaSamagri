package com.slyked.admin.configuration.viewmodel

import com.slyked.admin.configuration.model.OfferData


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slyked.admin.api.ResponseData
import com.slyked.admin.configuration.model.AppConfigurationModel
import com.slyked.admin.configuration.model.BannerData
import com.slyked.admin.configuration.model.PromoCode
import com.slyked.admin.configuration.repository.ConfigurationRepository
import com.slyked.admin.models.CommonResponse
import com.slyked.poojasamagri.AppConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConfigurationViewModel(private val repository: ConfigurationRepository): ViewModel() {

   private  val offerMutableLiveData = MutableLiveData<ResponseData<OfferData>>()

    val offerLiveData: LiveData<ResponseData<OfferData>>
    get() = offerMutableLiveData

    private  val bannerMutableLiveData = MutableLiveData<ResponseData<BannerData>>()

    val bannerLiveData: LiveData<ResponseData<BannerData>>
        get() = bannerMutableLiveData
    fun getOfferList(page:Int)
    {
        viewModelScope.launch(Dispatchers.IO){
            offerMutableLiveData.postValue(ResponseData.Loading())
            val response =  repository.getOfferList(page);

            if (response.isSuccessful && response.body()!=null && response.body()!!.status != null &&
                response.body()!!.data!=null && response.body()!!.data?.promoCodes !=null && response.body()!!.status ==200)
            {
                    val data = response.body()!!.data
                    offerMutableLiveData.postValue(ResponseData.Successful(data))
            }else{
                offerMutableLiveData.postValue(ResponseData.Error(response.body()?.message ?: "An Error Occurred"))
            }

        }


    }

     fun getAppConfiguration()
    {
        viewModelScope.launch {
            if (repository.getConfigurations().isSuccessful && repository.getConfigurations()
                    .body() != null && repository.getConfigurations().body()!!.status == 200
            ) {
                val data = repository.getConfigurations().body()!!.data.appConfigration
                data?.let { configuration ->
                    AppConfiguration.delivery_charge = configuration.delivery_charge
                    AppConfiguration.delivery_time = configuration.delivery_time
                    AppConfiguration.min_order_amount = configuration.min_order_amount
                    AppConfiguration.phone_number = configuration.phone_number
                    AppConfiguration.store_status = configuration.store_status
                    AppConfiguration.whatsapp_number = configuration.whatsapp_number
                }


            }
        }

    }


    fun getBannerList()
    {
        viewModelScope.launch(Dispatchers.IO){
            bannerMutableLiveData.postValue(ResponseData.Loading())
            val response =  repository.getBanners();

            if (response.isSuccessful && response.body()!=null && response.body()!!.status ==200)
            {
                val data = response.body()!!.data
                bannerMutableLiveData.postValue(ResponseData.Successful(data))
            }else{
                bannerMutableLiveData.postValue(ResponseData.Error(response.body()?.message ?: "An Error Occurred"))
            }

        }


    }






}
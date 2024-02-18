package com.slyked.admin.configuration.viewmodel

import com.slyked.admin.configuration.model.OfferData


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slyked.admin.api.ResponseData
import com.slyked.admin.configuration.model.PromoCode
import com.slyked.admin.configuration.repository.ConfigurationRepository
import com.slyked.admin.models.CommonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConfigurationViewModel(private val repository: ConfigurationRepository): ViewModel() {

   private  val offerMutableLiveData = MutableLiveData<ResponseData<OfferData>>()

    val offerLiveData: LiveData<ResponseData<OfferData>>
    get() = offerMutableLiveData

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








}
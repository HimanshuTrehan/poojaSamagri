package com.slyked.admin.configuration.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.slyked.admin.configuration.repository.ConfigurationRepository
import com.slyked.admin.configuration.viewmodel.ConfigurationViewModel

class ConfigurationViewModelFactory(private val repository: ConfigurationRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ConfigurationViewModel(repository) as T
    }

}
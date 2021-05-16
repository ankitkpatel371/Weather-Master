package com.weather.master.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weather.master.data.api.ApiHelper
import com.weather.master.data.repository.Repository
import com.weather.master.ui.activity.main.MainViewModel
import com.weather.master.ui.fragments.citys.CityViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(MainViewModel::class.java) -> {
            MainViewModel(Repository(apiHelper)) as T
        }
        modelClass.isAssignableFrom(CityViewModel::class.java) -> {
            CityViewModel(Repository(apiHelper)) as T
        }
        else -> throw IllegalArgumentException("Unknown class name")
    }

}


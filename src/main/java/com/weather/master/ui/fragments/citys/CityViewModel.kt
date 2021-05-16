package com.weather.master.ui.fragments.citys

import androidx.lifecycle.liveData
import com.weather.master.core.App
import com.weather.master.data.repository.Repository
import com.weather.master.ui.base.BaseViewModel
import com.weather.master.utils.Resource
import kotlinx.coroutines.Dispatchers

class CityViewModel(private val mainRepository: Repository) : BaseViewModel(App.instance) {

    override fun subscribe() {

    }

    fun fetchWeatherInformation(
        lat: String = "37.87",
        lon: String = "145.02",
        exclude: String = "hourly,minutely"
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.fetchWeatherInformation(lat,lon, exclude)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}
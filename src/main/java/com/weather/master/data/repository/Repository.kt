package com.weather.master.data.repository

import com.weather.master.data.api.ApiHelper


class Repository(private val apiHelper: ApiHelper) {
    suspend fun fetchWeatherInformation(
        lat: String,
        lon: String,
        exclude: String
    ) = apiHelper.fetchWeatherInformation(lat, lon, exclude)
}
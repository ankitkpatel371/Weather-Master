package com.weather.master.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun fetchWeatherInformation(
        lat: String,
        lon: String,
        exclude: String
    ) = apiService.getData(lat, lon, exclude)
}
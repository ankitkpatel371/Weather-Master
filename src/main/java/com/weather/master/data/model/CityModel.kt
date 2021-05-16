package com.weather.master.data.model

class CityModel(
    private val latitude: Double?,
    private val longitude: Double?,
    private val city: String
) {
    var lat = latitude
    var lon = longitude
    var name = city
}

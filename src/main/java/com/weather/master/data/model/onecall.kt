package com.weather.master.data.model

data class onecall(
    val lat: String,
    val lon: String,
    val timezone: String,
    val timezone_offset: Int,
    val title: String,
    val vote_average: Double,
    val vote_count: Int,
    val current: current,
    val daily: List<daily>,
)

data class current(
    val clouds: String,
    val dew_point: String,
    val dt: String,
    val feels_like: String,
    val humidity: String,
    val pressure: String,
    val sunrise: String,
    val sunset: String,
    val temp: String,
    val uvi: String,
    val visibility: String,
    val weather: List<weather>,
    val wind_deg: String,
    val wind_gust: String,
    val wind_speed: String
)

data class daily(
    val temp: temp,
    val weather: List<weather>,
    val dt:String
)

data class weather(
    val description: String,
    val icon: String,
    val id: String,
    val main: String

)

data class temp(
    val day: String,
    val eve: String,
    val max: String,
    val min: String,
    val morn: String,
    val night: String
)

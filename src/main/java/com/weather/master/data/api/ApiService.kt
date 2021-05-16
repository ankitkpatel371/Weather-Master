package com.weather.master.data.api

import com.weather.master.BuildConfig
import com.weather.master.data.model.onecall
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("data/2.5/onecall")
    suspend fun getData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude: String,
        @Query("appid") appid: String = BuildConfig.APP_ID,
        @Query("units") units: String = BuildConfig.APP_UNITS
    ): onecall

}
package com.weather.master.data.api

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.weather.master.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    private const val CONNECTION_TIMEOUT_SEC = 120
    private const val WRITE_TIMEOUT_SEC = 120
    private const val READ_TIMEOUT_SEC = 120

    private fun getOkHttpClient(): OkHttpClient {
        var builder = OkHttpClient.Builder()
        builder.interceptors().addAll(getInterceptorList())
        if (BuildConfig.DEBUG) {
            builder = builder.addNetworkInterceptor(StethoInterceptor())
        }

        builder = builder.connectTimeout(CONNECTION_TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
        builder = builder.writeTimeout(WRITE_TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
        builder = builder.readTimeout(READ_TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)

        return builder.build()
    }

    private fun getInterceptorList(): ArrayList<Interceptor> {

        val interceptorList = ArrayList<Interceptor>()

        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        interceptorList.add(loggingInterceptor)
        return interceptorList
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}
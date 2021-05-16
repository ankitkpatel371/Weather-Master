package com.weather.master.extentions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weather.master.data.api.ApiHelper
import com.weather.master.data.api.RetrofitBuilder
import com.weather.master.ui.base.ViewModelFactory


fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProvider(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))).get(
        viewModelClass
    )

fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProvider(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))).get(
        viewModelClass
    )


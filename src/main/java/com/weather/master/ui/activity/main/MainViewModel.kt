package com.weather.master.ui.activity.main

import androidx.lifecycle.liveData
import com.weather.master.core.App
import com.weather.master.data.repository.Repository
import com.weather.master.ui.base.BaseViewModel
import com.weather.master.utils.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val mainRepository: Repository) : BaseViewModel(App.instance) {

    override fun subscribe() {

    }


}
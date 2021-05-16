package com.weather.master.ui.base


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel constructor(application: Application) : AndroidViewModel(application) {

    var showProgress: MutableLiveData<Boolean> = MutableLiveData()


    fun showProgressDialog() {
        showProgress.value = true
    }

    fun hideProgressDialog() {
        showProgress.value = false
    }

    abstract fun subscribe()

    open fun unsubscribe() {
        clearAllCalls()
    }

    private fun clearAllCalls() {

    }
}

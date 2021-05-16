package com.weather.master.core

import android.app.Application
import android.content.Context
import com.weather.master.BuildConfig
import com.facebook.stetho.Stetho


class App : Application() {

    companion object {
        lateinit var appContext: Context
        lateinit var instance: App
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext

        initDebuggers()
    }


    private fun initDebuggers() {

        if (BuildConfig.DEBUG) {
            Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .build()
            )
            Stetho.initializeWithDefaults(this)
        }
    }
}

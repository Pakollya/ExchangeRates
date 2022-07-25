package com.pakollya.exchangerates

import android.app.Application
import com.pakollya.exchangerates.di.AppComponent
import com.pakollya.exchangerates.di.DaggerAppComponent

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent
            .builder()
            .context(this)
            .build()
    }
}
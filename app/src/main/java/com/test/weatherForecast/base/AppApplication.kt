package com.test.weatherForecast.base

import android.app.Application
import com.test.weatherForecast.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(databaseModule, repoModule, viewModelModule, networkModule)
        }
    }

}
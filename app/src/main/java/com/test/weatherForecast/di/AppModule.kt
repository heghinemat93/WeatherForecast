package com.test.weatherForecast.di

import android.app.Application
import androidx.room.Room
import com.test.weatherForecast.db.AppDatabase
import com.test.weatherForecast.db.CityDAO
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "weathers")
            .build()
    }

    fun provideWeatherDao(database: AppDatabase): CityDAO {
        return database.cityDao()
    }

    single { provideDatabase(androidApplication()) }

    single { provideWeatherDao(get()) }
}

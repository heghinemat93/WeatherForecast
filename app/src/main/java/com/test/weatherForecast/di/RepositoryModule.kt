package com.test.weatherForecast.di

import com.test.weatherForecast.ui.main.CityWeatherRepo
import com.test.weatherForecast.ui.main.CityWeatherRepoImpl
import com.test.weatherForecast.network.ResponseHandler
import org.koin.dsl.module

val repoModule = module {

    single { ResponseHandler() }

    single<CityWeatherRepo> { CityWeatherRepoImpl(get(), get(), get()) }

}
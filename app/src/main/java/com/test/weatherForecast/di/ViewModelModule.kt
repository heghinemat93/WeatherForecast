package com.test.weatherForecast.di
import com.test.weatherForecast.ui.main.CityWeatherViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { CityWeatherViewModel(get()) }
}
package com.test.weatherForecast.ui.main

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.test.weatherForecast.data.model.CityWeatherResponse
import com.test.weatherForecast.db.City
import com.test.weatherForecast.network.ApiResult

interface CityWeatherRepo {

    suspend fun getCityWeather(cityName: String): ApiResult<CityWeatherResponse>

    fun getCitiesLiveDataFromDb():  LiveData<PagedList<City>>

    suspend fun getCitiesFromDb(): List<City>

    suspend fun insertCity(cityWeatherResponse: CityWeatherResponse)

    suspend fun updateCity(city: City, cityWeatherResponse: CityWeatherResponse): Int

    suspend fun updateCity(city: City): Int

    suspend fun deleteCity(city: City)

    suspend fun getLastCity():City?

}
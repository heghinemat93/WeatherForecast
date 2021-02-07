package com.test.weatherForecast.ui.main

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.test.weatherForecast.base.Constants.Companion.PAGE_CITY_ITEM
import com.test.weatherForecast.data.model.CityWeatherResponse
import com.test.weatherForecast.db.City
import com.test.weatherForecast.db.CityDAO
import com.test.weatherForecast.db.Weather
import com.test.weatherForecast.network.ApiResult
import com.test.weatherForecast.network.CityWeatherApi
import com.test.weatherForecast.network.ResponseHandler

class CityWeatherRepoImpl(
    private val cityWeatherApi: CityWeatherApi,
    private val responseHandler: ResponseHandler,
    private val dao: CityDAO
) : CityWeatherRepo {

    override suspend fun getCityWeather(cityName: String): ApiResult<CityWeatherResponse> {
        return try {
            val data = cityWeatherApi.getWeather(cityName)
            responseHandler.handleSuccess(data)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    override fun getCitiesLiveDataFromDb(): LiveData<PagedList<City>> {
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_CITY_ITEM)
            .setPageSize(PAGE_CITY_ITEM)
            .setEnablePlaceholders(true)
            .build()

        return LivePagedListBuilder(dao.getAllWithLiveData(), config)
            .build()
    }

    override suspend fun getCitiesFromDb(): List<City> {
        return dao.getAll()
    }

    override suspend fun insertCity(cityWeatherResponse: CityWeatherResponse) {
        val weather = convertWeather(cityWeatherResponse)
        val city = getLastCity()
        var order = 1
        if (city != null) {
            order = city.order
        }
        dao.insert(City(name = cityWeatherResponse.name, weather = weather, order = order + 1))
    }

    override suspend fun updateCity(city: City, cityWeatherResponse: CityWeatherResponse): Int {
        val weather = convertWeather(cityWeatherResponse)
        city.weather = weather
        return dao.update(city)
    }

    override suspend fun updateCity(city: City): Int {
        return dao.update(city)
    }

    override suspend fun deleteCity(city: City) {
        return dao.delete(city)
    }

    override suspend fun getLastCity(): City? {
        return dao.getLast()
    }

    private fun convertWeather(weatherResponse: CityWeatherResponse): Weather {
        val weather = weatherResponse.mainWeather
        return Weather(
            weather.temp,
            weather.feels_like,
            weather.temp_min,
            weather.temp_max,
            weather.pressure,
            weather.humidity,
            weatherResponse.weatherDescList[0].icon
        )
    }

}
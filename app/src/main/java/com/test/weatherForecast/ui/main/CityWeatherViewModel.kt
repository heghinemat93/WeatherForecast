package com.test.weatherForecast.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.test.weatherForecast.data.model.CityWeatherResponse
import com.test.weatherForecast.db.City
import com.test.weatherForecast.network.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CityWeatherViewModel(private val repoCity: CityWeatherRepo) : ViewModel() {

    private val _weatherData = MutableLiveData<ApiResult<CityWeatherResponse>>()
    val cityWeatherData: LiveData<ApiResult<CityWeatherResponse>> get() = _weatherData
    var cityList: LiveData<PagedList<City>>? = null

    fun getWeatherByCityName(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repoCity.getCityWeather(city)
            response.data?.let { repoCity.insertCity(it) }
            withContext(Dispatchers.Main) {
                _weatherData.value = response
            }
        }
    }

    fun getCachedCityWeathersLiveData() {
        cityList = repoCity.getCitiesLiveDataFromDb()
    }

    fun getCachedCityWeathersAndUpdate() {
        viewModelScope.launch(Dispatchers.IO) {
            val cities = repoCity.getCitiesFromDb()
            withContext(Dispatchers.Main) {
                updateAllCityWeathers(cities)
            }
        }
    }

    fun deleteCity(city: City) {
        viewModelScope.launch(Dispatchers.IO) {
            repoCity.deleteCity(city)
        }
    }

    private fun updateAllCityWeathers(fetchedCities: List<City>) {
        fetchedCities.forEach {
            fetchAndUpdateCity(it)
        }
    }

    fun fetchAndUpdateCity(city: City) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repoCity.getCityWeather(city.name)
            response.data?.let { it1 ->
                repoCity.updateCity(city, it1)
            }
        }
    }

    fun updateCity(city: City) {
        viewModelScope.launch(Dispatchers.IO) {
            repoCity.updateCity(city)
        }
    }

}
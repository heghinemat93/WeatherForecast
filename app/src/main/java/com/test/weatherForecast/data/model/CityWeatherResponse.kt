package com.test.weatherForecast.data.model

import com.google.gson.annotations.SerializedName

data class CityWeatherResponse(
    val id: String,
    @SerializedName("main") val mainWeather: WeatherResponse,
    @SerializedName("weather") val weatherDescList: List<WeatherDesc>,
    val name: String,
    val message: String
){
    data class WeatherResponse(
        val temp: Double, val feels_like: Double, val temp_min: String, val temp_max: String,
        val pressure: String, val humidity: String
    )

    data class WeatherDesc(val icon: String)
}
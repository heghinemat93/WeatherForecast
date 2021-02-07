package com.test.weatherForecast.db

data class Weather(
    val temp: Double,
    val feels_like: Double,
    val temp_min: String,
    val temp_max: String,
    val pressure: String,
    val humidity: String,
    val icon: String
)
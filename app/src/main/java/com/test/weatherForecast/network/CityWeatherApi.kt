package com.test.weatherForecast.network

import com.test.weatherForecast.data.model.CityWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CityWeatherApi {

    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") cityName: String
    ): CityWeatherResponse

}
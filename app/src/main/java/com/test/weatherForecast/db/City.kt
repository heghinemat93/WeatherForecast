package com.test.weatherForecast.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var order: Int,
    val name: String,
    @Embedded var weather: Weather
)
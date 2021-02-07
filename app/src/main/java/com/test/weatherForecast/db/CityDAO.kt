package com.test.weatherForecast.db

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface CityDAO {

    @Insert
    suspend fun insert(vararg city: City)

    @Delete
    suspend fun delete(city: City)

    @Update
    suspend fun update(city: City): Int

    @Query("SELECT * FROM city ORDER BY `order`")
    fun getAllWithLiveData(): DataSource.Factory<Int, City>

    @Query("SELECT * FROM city ORDER BY `order`")
    suspend fun getAll(): List<City>

    @Query("SELECT * FROM City ORDER BY `order` DESC LIMIT 1")
    suspend fun getLast(): City?
}
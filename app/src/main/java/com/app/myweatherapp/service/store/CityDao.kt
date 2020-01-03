package com.app.myweatherapp.service.store

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.myweatherapp.service.model.CityModel

/**
 * Created by mayank on January 03 2020
 */

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(cityModel: CityModel)

    @Query("SELECT * FROM recentCities order by addedOn DESC LIMIT 10")
    fun getCities(): List<CityModel>
}
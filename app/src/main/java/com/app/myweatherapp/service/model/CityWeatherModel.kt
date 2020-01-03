package com.app.myweatherapp.service.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by mayank on January 02 2020
 */

@Entity(tableName = "recentCities")
data class CityModel(@PrimaryKey var city:String, var addedOn:Long)
data class CityWeatherModel(var city:String, var weatherImage: String, var humidity: String, var weatherText: String, var temperature:String)
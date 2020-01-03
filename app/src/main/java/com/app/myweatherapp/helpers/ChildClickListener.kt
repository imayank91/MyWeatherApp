package com.app.myweatherapp.helpers

import com.app.myweatherapp.service.model.CityModel
import com.app.myweatherapp.service.model.CityWeatherModel

/**
 * Created by mayank on October 11 2019
 */

interface ChildClickListener {
    fun onChildClick(cityModel: CityModel)
}
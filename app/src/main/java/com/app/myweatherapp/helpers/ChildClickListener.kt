package com.app.myweatherapp.helpers

import com.app.myweatherapp.service.model.CityModel

/**
 * Created by mayank on January 02 2020
 */

interface ChildClickListener {
    fun onChildClick(cityModel: CityModel)
}
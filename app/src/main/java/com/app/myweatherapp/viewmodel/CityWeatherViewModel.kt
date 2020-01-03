package com.app.myweatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.myweatherapp.service.model.CityWeatherModel
import com.app.myweatherapp.service.repository.CityWeatherRepository

/**
 * Created by mayank on January 02 2020
 */
class CityWeatherViewModel : ViewModel() {

    private val cityWeatherRepository = CityWeatherRepository()

    var cityWeatherModel = MutableLiveData<CityWeatherModel>()

    init {
        cityWeatherModel = cityWeatherRepository.cityWeather
    }

    fun searchCityAndWeather(cityName: String) {
            cityWeatherRepository.fetchCity(cityName)
    }
}
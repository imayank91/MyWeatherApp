package com.app.myweatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.myweatherapp.service.model.CityModel
import com.app.myweatherapp.service.repository.CitySearchRepository

/**
 * Created by mayank on January 03 2020
 */
class CitySearchViewModel : ViewModel() {

    private val citySearchRepository = CitySearchRepository()

    var cityModel = MutableLiveData<MutableList<CityModel>>()

    init {
        cityModel = citySearchRepository.cityList
    }

    fun searchCityAndWeather(cityName: String) {
            citySearchRepository.fetchCity(cityName)
    }

    fun fetchRecentCities(){
        citySearchRepository.fetchRecentCities()
    }
}
package com.app.myweatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.myweatherapp.service.model.CityWeatherModel
import com.app.myweatherapp.service.repository.CityWeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by mayank on January 02 2020
 */
class CityWeatherViewModel : ViewModel() {

    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO
    private val scope = CoroutineScope(coroutineContext)

    private val cityWeatherRepository = CityWeatherRepository()

    var cityWeatherModel = MutableLiveData<MutableList<CityWeatherModel>>()

    init {
        cityWeatherModel = cityWeatherRepository.cityList
    }

    fun searchCityAndWeather() {
        scope.launch {
            cityWeatherRepository.fetchCity()
        }
    }
}
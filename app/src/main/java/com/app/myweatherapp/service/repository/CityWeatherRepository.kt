package com.app.myweatherapp.service.repository

import androidx.lifecycle.MutableLiveData
import com.app.myweatherapp.service.model.CityWeatherModel
import com.app.myweatherapp.utils.NetworkUtils
import com.app.myweatherapp.utils.StringContract
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.URL
import kotlin.coroutines.CoroutineContext

/**
 * Created by mayank on January 02 2020
 */
class CityWeatherRepository {

    var cityWeather = MutableLiveData<CityWeatherModel>()

    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO
    private val scope = CoroutineScope(coroutineContext)

    private fun provideURL(cityName: String): URL? {
        val apiKey = StringContract.apiKey
        val urlBuilder =
            StringBuilder(StringContract.weatherUrl)
        urlBuilder.append("?")
        urlBuilder.append("&key=").append(apiKey)
        urlBuilder.append("&q=").append(cityName)
        urlBuilder.append("&format=json")

        return URL(urlBuilder.toString())
    }

    fun fetchCity(cityName: String) {
        scope.launch {
            val url = provideURL(cityName)
            parseResponse(NetworkUtils.fetchData(url!!))
        }
    }

    private suspend fun parseResponse(response:String) {
        val data = JSONObject(response)
        val requestArray = data.getJSONObject("data").getJSONArray("request")
        val cityObject = requestArray.getJSONObject(0)
        val cityName = cityObject.getString("query")
        val currentconditionArr= data.getJSONObject("data").getJSONArray("current_condition")
        val currentConditionObj = currentconditionArr.getJSONObject(0)
        var currentTemperature= currentConditionObj.getString("temp_C")
        currentTemperature = "$currentTemperature °C"

        var humidity = currentConditionObj.getString("humidity")
        humidity = "Humidity: $humidity %"

        val weatherDesc = currentConditionObj.getJSONArray("weatherDesc").getJSONObject(0).getString("value")
        val weatherIconUrl = currentConditionObj.getJSONArray("weatherIconUrl").getJSONObject(0).getString("value")

        val cityWeatherModel = CityWeatherModel(cityName, weatherIconUrl, humidity, weatherDesc, currentTemperature)

        withContext(Dispatchers.Main) {
            cityWeather.value = cityWeatherModel
        }

    }

}
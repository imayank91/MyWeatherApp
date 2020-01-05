package com.app.myweatherapp.service.repository

import androidx.lifecycle.MutableLiveData
import com.app.myweatherapp.MyWeatherApp
import com.app.myweatherapp.service.model.CityModel
import com.app.myweatherapp.utils.NetworkUtils
import com.app.myweatherapp.utils.StringContract
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.URL
import kotlin.coroutines.CoroutineContext

/**
 * Created by mayank on January 03 2020
 */
class CitySearchRepository {

    var cityList = MutableLiveData<MutableList<CityModel>>()

    private var mutableCityList = mutableListOf<CityModel>()

    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO
    private val scope = CoroutineScope(coroutineContext)

    private fun provideURL(cityName: String): URL? {
        val apiKey = StringContract.apiKey
        val urlBuilder =
            StringBuilder(StringContract.searchUrl)
        urlBuilder.append("?")
        urlBuilder.append("&key=").append(apiKey)
        urlBuilder.append("&q=").append(cityName)
        urlBuilder.append("&format=json")

        return URL(urlBuilder.toString())
    }

    fun fetchRecentCities() {
        scope.launch {

           val cities = MyWeatherApp.database!!.cityDao().getCities()
           if(cities.isNotEmpty()){

               withContext(Dispatchers.Main) {
                   mutableCityList.clear()
                   mutableCityList.addAll(cities)
                   cityList.value = mutableCityList
               }
           }
        }
    }

    fun fetchCity(cityName: String) {
        scope.launch {
            val url = provideURL(cityName)
            parseResponse(NetworkUtils.fetchData(url!!))
        }
    }


    private suspend fun parseResponse(response: String) {

        val data = JSONObject(response)
        val resultArray = data.getJSONObject("search_api").getJSONArray("result")

        mutableCityList.clear()
        for (i in 0 until resultArray.length()) {

            val resultObj = resultArray.getJSONObject(i)
            var cityName = resultObj.getJSONArray("areaName").getJSONObject(0).getString("value")
            val region = resultObj.getJSONArray("region").getJSONObject(0).getString("value")
            val country = resultObj.getJSONArray("country").getJSONObject(0).getString("value")

            cityName = "$cityName, $region, $country"

            val cityModel = CityModel(cityName, System.currentTimeMillis())
            mutableCityList.add(cityModel)

            withContext(Dispatchers.Main) {
                cityList.value = mutableCityList
            }
        }

    }
}
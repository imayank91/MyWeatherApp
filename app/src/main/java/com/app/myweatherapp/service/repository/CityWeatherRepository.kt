package com.app.myweatherapp.service.repository

import android.util.Log
import com.app.myweatherapp.service.model.CityWeatherModel
import com.app.myweatherapp.utils.StringContract
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by mayank on January 02 2020
 */
class CityWeatherRepository {

    private var cityList = mutableListOf<CityWeatherModel>()

    private fun provideURL(cityName: String): URL? {
        val apiKey = StringContract.apiKey
        val urlBuilder =
            StringBuilder(StringContract.url)
        urlBuilder.append("?")
        urlBuilder.append("&key=").append(apiKey)
        urlBuilder.append("&q=").append(cityName)
        urlBuilder.append("&format=json")

        return URL(urlBuilder.toString())
    }

    fun fetchCity() {
        var response = ""

        val url = provideURL("Delhi")
        Log.i("URL", url.toString())
        val urlConnection =
            url!!.openConnection() as HttpURLConnection
        if (urlConnection.responseCode == 200) {
            val inputStreamReader =
                InputStreamReader(urlConnection.inputStream)
            val r = BufferedReader(inputStreamReader)
            val stringBuilder = java.lang.StringBuilder()
            var line: String?
            while (r.readLine().also { line = it } != null) {
                stringBuilder.append(line)
                stringBuilder.append("\n")
            }
            response += stringBuilder.toString()
            urlConnection.disconnect()
            parseResponse(response)
            Log.i("Response is ", response)
            // Background work finished successfully
        } else { // Bad response from server
            Log.i("Task", "bad response " + urlConnection.responseCode)
        }
    }

    private fun parseResponse(response:String) {
        val data = JSONObject(response)
        val requestArray = data.getJSONObject("data").getJSONArray("request")
        val cityObject = requestArray.getJSONObject(0)
        val cityName = cityObject.getString("query")
        val currentconditionArr= data.getJSONObject("data").getJSONArray("current_condition")
        val currentConditionObj = currentconditionArr.getJSONObject(0)
        val currentTemperature= currentConditionObj.getString("temp_C")
        val humidity = currentConditionObj.getString("humidity")
        val weatherDesc = currentConditionObj.getJSONArray("weatherDesc").getJSONObject(0).getString("value")
        val weatherIconUrl = currentConditionObj.getJSONArray("weatherIconUrl").getJSONObject(0).getString("value")

        val cityWeatherModel = CityWeatherModel(weatherIconUrl, humidity, weatherDesc, currentTemperature)

        cityList.add(cityWeatherModel)
    }

}
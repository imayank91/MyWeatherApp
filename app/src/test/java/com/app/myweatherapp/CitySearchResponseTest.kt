package com.app.myweatherapp

import com.app.myweatherapp.utils.NetworkUtils
import com.app.myweatherapp.utils.StringContract
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.net.URL

/**
 * Created by mayank on January 03 2020
 */
class CitySearchResponseTest {

    private lateinit var url: URL
    @Before
    fun provideURL() {
        val apiKey = StringContract.apiKey
        val urlBuilder =
            StringBuilder(StringContract.searchUrl)
        urlBuilder.append("?")
        urlBuilder.append("&key=").append(apiKey)
        urlBuilder.append("&q=").append("Mumbai")
        urlBuilder.append("&format=json")

        url = URL(urlBuilder.toString())
    }

    @Test
    fun testWeatherApiResponse() {
        val response = NetworkUtils.fetchData(url)
        Assert.assertNotEquals("",response)
    }
}
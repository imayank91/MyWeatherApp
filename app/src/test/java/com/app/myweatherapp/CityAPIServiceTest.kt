package com.app.myweatherapp

import com.app.myweatherapp.utils.NetworkUtils
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.BufferedSource
import okio.Okio
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.nio.charset.StandardCharsets


/**
 * Created by mayank on January 03 2020
 */


class CityAPIServiceTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun initService() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String) {
        val inputStream = CityAPIServiceTest::class.java.classLoader!!.getResourceAsStream(String.format("api-response/%s", fileName))
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        mockWebServer.enqueue(mockResponse.setBody((source as BufferedSource).readString(
            StandardCharsets.UTF_8)))
    }

    @Test
    @Throws(IOException::class)
    fun testCitySearch() {
        enqueueResponse("city_search.json")
        val response = NetworkUtils.fetchData(mockWebServer.url("/").url())
        Assert.assertNotEquals("", response)
    }

    @After
    fun closeService(){
        mockWebServer.shutdown()
    }

}
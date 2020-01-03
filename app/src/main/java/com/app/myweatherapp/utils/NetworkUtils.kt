package com.app.myweatherapp.utils

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by mayank on January 03 2020
 */
object NetworkUtils {

    fun fetchData(url: URL): String {
        var response = ""
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
            Log.i("Response is ", response)

            // Background work finished successfully
        } else { // Bad response from server
            Log.i("Task", "bad response " + urlConnection.responseCode)
        }

        return response
    }
}
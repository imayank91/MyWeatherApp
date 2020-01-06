package com.app.myweatherapp.helpers

import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import kotlinx.coroutines.*
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.CoroutineContext


/**
 * Created by mayank on January 03 2020
 */

private var parentJob = Job()

private val coroutineContext: CoroutineContext
    get() = parentJob + Dispatchers.IO
private val scope = CoroutineScope(coroutineContext)

@BindingAdapter("weatherImage")
fun loadImage(view: ImageView, image_location: String?) {
    scope.launch {
        var imageURL: URL? = null
        if (image_location != null) {
            try {
                imageURL = URL(image_location)
                val connection: HttpURLConnection = imageURL
                    .openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val inputStream: InputStream = connection.inputStream
                val bitmap = BitmapFactory.decodeStream(inputStream)
                withContext(Dispatchers.Main){
                    view.setImageBitmap(bitmap)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}
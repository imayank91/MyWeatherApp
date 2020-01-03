package com.app.myweatherapp

import android.app.Application
import androidx.room.Room
import com.app.myweatherapp.service.store.AppDatabase

/**
 * Created by mayank on January 03 2020
 */
class MyWeatherApp : Application() {

    companion object {
        var database: AppDatabase? = null
    }

    private fun initDatabase() {
        database =
            Room.databaseBuilder(applicationContext, AppDatabase::class.java, "weatherapp_db")
                .fallbackToDestructiveMigration().build()
    }

    override fun onCreate() {
        super.onCreate()
        initDatabase()
    }

}
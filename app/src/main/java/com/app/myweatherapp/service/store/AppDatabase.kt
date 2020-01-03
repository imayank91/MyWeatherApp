package com.app.myweatherapp.service.store

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.myweatherapp.service.model.CityModel

/**
 * Created by mayank on January 03 2020
 */
@Database(entities = [(CityModel::class)], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cityDao(): CityDao
}
package com.app.myweatherapp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.app.myweatherapp.service.model.CityModel
import com.app.myweatherapp.service.store.AppDatabase
import com.app.myweatherapp.service.store.CityDao
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Created by mayank on January 03 2020
 */

@RunWith(AndroidJUnit4::class)
class EntityReadWriteTest {
    private lateinit var cityDao: CityDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().context
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        cityDao = db.cityDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeCityAndReadInList() {
        val city = CityModel("New Delhi", System.currentTimeMillis())
        cityDao.insertCity(city)
        val citiItem = cityDao.getCities()[0]
        assertThat(citiItem, equalTo(citiItem))
    }


}
package com.app.myweatherapp

import com.app.myweatherapp.fragments.CityListFragment
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

/**
 * Created by mayank on January 05 2020
 */

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    lateinit var mainActivity : MainActivity

    @Before
    fun setUp() {
        mainActivity = Robolectric.buildActivity(MainActivity::class.java).create().get()
    }

    @Test
    fun assertActivityNotNull(){
        Assert.assertNotNull(mainActivity)
    }

    @Test
    fun shouldHaveCityListFragment() {
        val navHostFragment = mainActivity.supportFragmentManager.findFragmentById(R.id.fragment)
        Assert.assertNotNull(navHostFragment!!.childFragmentManager.fragments.first() as CityListFragment)

    }
}
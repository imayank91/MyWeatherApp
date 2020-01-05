package com.app.myweatherapp

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import com.app.myweatherapp.fragments.WeatherDetailFragment
import com.app.myweatherapp.utils.StringContract
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Created by mayank on January 05 2020
 */

@RunWith(RobolectricTestRunner::class)
class WeatherDetailFragmentTest {

    lateinit var weatherDetailFragment: WeatherDetailFragment

    lateinit var scenario: FragmentScenario<WeatherDetailFragment>

    @Before
    fun setUp() {
        val bundle = Bundle().apply {
            putString(StringContract.CITY_NAME, "Delhi")
        }
        scenario = launchFragmentInContainer(
            fragmentArgs = bundle,
            themeResId = R.style.AppTheme
        )
        scenario.onFragment {
            weatherDetailFragment = it
        }
    }

    @Test
    fun assertFragmentNotNull() {
        Assert.assertNotNull(weatherDetailFragment)
    }
}
package com.app.myweatherapp

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer

import com.app.myweatherapp.fragments.CityListFragment
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Created by mayank on January 05 2020
 */

@RunWith(RobolectricTestRunner::class)
class CityListFragmentTest {

    lateinit var cityListFragment: CityListFragment

    lateinit var scenario: FragmentScenario<CityListFragment>

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme)
        scenario.onFragment {
            cityListFragment = it
        }
    }

    @Test
    fun assertFragmentNotNull() {
        Assert.assertNotNull(cityListFragment)
    }

}
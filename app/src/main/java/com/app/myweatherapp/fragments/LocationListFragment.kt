package com.app.myweatherapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.app.myweatherapp.R
import com.app.myweatherapp.databinding.FragmentLocationListBinding
import com.app.myweatherapp.viewmodel.CityWeatherViewModel

/**
 * Created by mayank on January 02 2020
 */
class LocationListFragment : Fragment() {

    private lateinit var binding: FragmentLocationListBinding
    private lateinit var viewModel:CityWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_location_list, container, false)
        viewModel = ViewModelProviders.of(this).get(CityWeatherViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.searchCityAndWeather()
    }
}
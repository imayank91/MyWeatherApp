package com.app.myweatherapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.app.myweatherapp.R
import com.app.myweatherapp.databinding.LayoutWeatherDetailBinding
import com.app.myweatherapp.utils.StringContract
import com.app.myweatherapp.viewmodel.CityWeatherViewModel

/**
 * Created by mayank on January 03 2020
 */
class WeatherDetailFragment : Fragment() {

    private lateinit var binding: LayoutWeatherDetailBinding
    private lateinit var viewModel: CityWeatherViewModel
    private lateinit var cityName:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.layout_weather_detail, container, false)
        viewModel = ViewModelProviders.of(this).get(CityWeatherViewModel::class.java)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cityName = arguments?.getString(StringContract.CITY_NAME)!!

        setToolbar()
        viewModel.searchCityAndWeather(cityName)

        viewModel.cityWeatherModel.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.progressBar.visibility = View.GONE
                binding.model = it
            }
        })
    }

    private fun setToolbar() {
        binding.toolbar.toolbarHome.setNavigationIcon(R.drawable.baseline_arrow_back_white_24)
        binding.toolbar.toolbarHome.title = getString(R.string.todays_weather)
        binding.toolbar.toolbarHome.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

    }
}
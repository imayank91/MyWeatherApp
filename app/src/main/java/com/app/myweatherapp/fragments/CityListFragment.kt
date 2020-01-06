package com.app.myweatherapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.myweatherapp.MyWeatherApp
import com.app.myweatherapp.R
import com.app.myweatherapp.adapter.CityListAdapter
import com.app.myweatherapp.databinding.FragmentCityListBinding
import com.app.myweatherapp.helpers.ChildClickListener
import com.app.myweatherapp.service.model.CityModel
import com.app.myweatherapp.utils.NetworkUtils.isConnectedToNetwork
import com.app.myweatherapp.utils.StringContract
import com.app.myweatherapp.viewmodel.CitySearchViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by mayank on January 02 2020
 */
class CityListFragment : Fragment(), ChildClickListener {

    private lateinit var binding: FragmentCityListBinding
    private lateinit var viewModel: CitySearchViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var cityListAdapter: CityListAdapter

    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO
    private val scope = CoroutineScope(coroutineContext)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_city_list, container, false)
        viewModel = ViewModelProviders.of(this).get(CitySearchViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMenuAndSearch()
        linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        cityListAdapter = CityListAdapter(context!!, this)
        binding.recyclerView.adapter = cityListAdapter

        viewModel.fetchRecentCities()

        viewModel.cityModel.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.searchCityTextView.visibility = View.GONE
                cityListAdapter.setCityList(it)
            }

        })
        checkNetwork()

    }

    private fun setMenuAndSearch() {
        binding.toolbar.toolbarHome.inflateMenu(R.menu.menu_main)
        binding.toolbar.toolbarHome.menu.getItem(0).setOnMenuItemClickListener {
            if(isConnectedToNetwork(context!!)){
                searchCity()
            }else{
                checkNetwork()
            }

            true
        }
    }

    private fun searchCity(){

        val input = EditText(context)
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.maxLines = 1

        val inputLayout = TextInputLayout(context)
        inputLayout.setPadding(32, 0, 32, 0)
        inputLayout.addView(input)

        val alert: AlertDialog.Builder = AlertDialog.Builder(context)
        alert.setTitle(this.getString(R.string.search_title))
        alert.setView(inputLayout)

        alert.setPositiveButton(R.string.dialog_ok
        ) { _, _ ->
            val result: String = input.text.toString()
            if (result.isNotEmpty()) {
                viewModel.searchCityAndWeather(result)
            }
        }
        alert.setNegativeButton(R.string.dialog_cancel
        ) { _, _ ->
            // Cancelled
        }
        alert.show()
    }

    private fun checkNetwork() {
        if(!isConnectedToNetwork(context!!)){
            Snackbar.make(view!!, "No internet Connection", Snackbar.LENGTH_LONG).show()
        }
    }


    override fun onChildClick(cityModel: CityModel) {
        scope.launch {
            MyWeatherApp.database!!.cityDao().insertCity(cityModel)
        }
        arguments = Bundle().apply {
            putString(StringContract.CITY_NAME, cityModel.city)
        }
        findNavController().navigate(R.id.action_cityListFragment_to_weatherDetailFragment,arguments)

    }

}
package com.app.myweatherapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.myweatherapp.R
import com.app.myweatherapp.databinding.ListLocationRowBinding
import com.app.myweatherapp.helpers.ChildClickListener
import com.app.myweatherapp.service.model.CityModel
import com.app.myweatherapp.service.model.CityWeatherModel
import com.app.myweatherapp.viewholder.CityListViewHolder

/**
 * Created by mayank on January 02 2020
 */
class CityListAdapter(
    private val context: Context,
    private val childClickListener: ChildClickListener?
) : RecyclerView.Adapter<CityListViewHolder>() {

    private var mutableCityList = mutableListOf<CityModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityListViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)

        val listLocationRowBinding: ListLocationRowBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_location_row, parent, false)

        return CityListViewHolder(listLocationRowBinding)
    }

    override fun getItemCount(): Int {
        return mutableCityList.size
    }

    override fun onBindViewHolder(holder: CityListViewHolder, position: Int) {
        val item = mutableCityList[position]
        holder.binding.model = item
        holder.binding.childClick = childClickListener
    }

    fun setCityList(cityList: MutableList<CityModel>) {
        this.mutableCityList = cityList
        notifyDataSetChanged()
    }

}
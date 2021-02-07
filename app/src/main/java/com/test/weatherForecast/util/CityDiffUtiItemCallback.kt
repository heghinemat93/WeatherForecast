package com.test.weatherForecast.util

import androidx.recyclerview.widget.DiffUtil
import com.test.weatherForecast.db.City

class CityDiffUtiItemCallback : DiffUtil.ItemCallback<City>() {

    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem == newItem
    }
}
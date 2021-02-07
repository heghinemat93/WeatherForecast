package com.test.weatherForecast.util

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.test.weatherForecast.db.City

class CityDiffUtil(private val oldList: List<City>, private val newList: List<City>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList[oldPosition].name == newList[newPosition].name
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }

}
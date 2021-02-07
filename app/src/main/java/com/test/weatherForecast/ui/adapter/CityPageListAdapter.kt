package com.test.weatherForecast.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.test.weatherForecast.R
import com.test.weatherForecast.db.City
import com.test.weatherForecast.util.CityDiffUtiItemCallback
import kotlinx.android.synthetic.main.city_item.view.*

class CityPageListAdapter(val longClickListener: (City) -> Unit) :
    PagedListAdapter<City, CityPageListAdapter.CityViewHolder>(CityDiffUtiItemCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.city_item, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val cityWeather = getItem(position)
        cityWeather?.let { holder.bind(it, longClickListener) }
    }

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(city: City, clickListener: (City) -> Unit) {
            itemView.apply {
                val iconUrl = "https://openweathermap.org/img/w/${city.weather.icon}.png"
                Glide.with(context).load(iconUrl).apply(RequestOptions.circleCropTransform())
                    .into(icon)


                temp.text = city.weather.temp.toString()
                feels_like.text = city.weather.feels_like.toString()
                city_name.text = city.name

                itemView.setOnLongClickListener {
                    clickListener(city)
                    true
                }
            }

        }
    }


}
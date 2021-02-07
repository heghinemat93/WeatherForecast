package com.test.weatherForecast.ui.main

import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.weatherForecast.R
import com.test.weatherForecast.data.model.CityWeatherResponse
import com.test.weatherForecast.network.ApiResult
import com.test.weatherForecast.network.Status
import com.test.weatherForecast.ui.adapter.CityPageListAdapter
import com.test.weatherForecast.util.NetworkHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.progress_layout.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val viewModel: CityWeatherViewModel by viewModel()
    private var cityName: String? = null
    private lateinit var cityAdapter: CityPageListAdapter
    private val itemTouchHelper by lazy {
        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(
                UP or
                        DOWN or
                        START or
                        END, 0
            ) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int
                ) {
                }

                override fun onMoved(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    fromPos: Int,
                    target: RecyclerView.ViewHolder,
                    toPos: Int,
                    x: Int,
                    y: Int
                ) {
                    super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y)

                    val fromCity = cityAdapter.currentList?.get(fromPos)
                    val toCity = cityAdapter.currentList?.get(toPos)

                    fromCity?.order = toPos
                    toCity?.order = fromPos

                    fromCity?.let { viewModel.updateCity(it) }
                    toCity?.let { viewModel.updateCity(it) }
                }
            }

        ItemTouchHelper(simpleItemTouchCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cities_recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            cityAdapter = CityPageListAdapter(clickListener = {
                viewModel.deleteCity(it)
            })
            adapter = cityAdapter
        }
        itemTouchHelper.attachToRecyclerView(cities_recyclerview)

        observeCtyWeather()
        setPlusOnClickListener()

        viewModel.getCachedCityWeathersLiveData()
        observeCachedCityWeathers()
    }

    private fun setPlusOnClickListener() {
        plus.setOnClickListener {
            openAddCityDialog()
        }

    }

    private fun checkNetworkAndDoUpdates() {
        if (NetworkHelper.isNetworkAvailable(this)) {
            viewModel.getCachedCityWeathersAndUpdate()
        } else {
            showServerErrorAlert(getString(R.string.enable_internet))
        }
    }

    private fun observeCachedCityWeathers() {
        viewModel.cityList?.observe(this, {
            if (it.isNullOrEmpty()) {
                checkNetworkAndDoUpdates()
            } else {
                cityAdapter.submitList(it)
            }
        })
    }

    private fun observeCtyWeather() {
        viewModel.cityWeatherData.observe(this, {
            consumeWeatherResponse(it)
        })
    }

    private fun consumeWeatherResponse(it: ApiResult<CityWeatherResponse>?) {
        when (it?.status) {
            Status.LOADING -> {
                pb_loading.visibility = View.VISIBLE
            }
            Status.SUCCESS -> {
                pb_loading.visibility = View.GONE
                if (!TextUtils.isEmpty(it.data?.message)) {
                    showServerErrorAlert(it.data?.message)
                }
            }
            Status.ERROR -> {
                pb_loading.visibility = View.GONE
                showServerErrorAlert(it.exception?.message)
            }
        }
    }

    private fun openAddCityDialog() {
        val view = layoutInflater.inflate(R.layout.add_city_view, null)
        val nextBtn = view.findViewById<Button>(R.id.btn_next)
        val cityTxt = view.findViewById<TextView>(R.id.city_txt)


        val dialog = AlertDialog.Builder(this)
            .setView(view)
            .create()
        nextBtn.setOnClickListener {
            cityName = cityTxt.text.toString()
            if (!TextUtils.isEmpty(cityName)) {
                viewModel.getWeatherByCityName(cityName!!)
            }
            dialog.dismiss()
        }
        dialog.show()

    }


    private fun showServerErrorAlert(message: String?) {
        val builder = android.app.AlertDialog.Builder(this, R.style.AlertDialogTheme)
        builder.setMessage(message)
        builder.setPositiveButton(R.string.ok) { dialogInterface, _ ->
            dialogInterface.cancel()
        }
        builder.setCancelable(true)
        val dialog = builder.create()
        dialog.show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE)
            .setTextColor(ContextCompat.getColor(this, R.color.design_default_color_primary))
    }


}
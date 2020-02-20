package com.project.test_3ss.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.test_3ss.R
import com.project.test_3ss.data.models.ForecastListModel
import com.project.test_3ss.utils.*
import kotlinx.android.synthetic.main.forecast_item_layout.view.*

class ForecastAdapter(
    var forecastList: MutableList<ForecastListModel>,
    var isMultipleDays: Boolean = false
) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_item_layout, parent, false)
        return ForecastViewHolder(v)
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.displayForecast(forecastList[position])
    }

    fun updateList(newForecastList: MutableList<ForecastListModel>, multipleDays: Boolean) {
        forecastList.clear()
        forecastList.addAll(newForecastList)
        isMultipleDays = multipleDays
        notifyDataSetChanged()
    }

    inner class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun displayForecast(item: ForecastListModel) {

            itemView.forecast_time_tv.text = item.dtText.stringToDate().getHourMinDisplay()

            when (item.weather.first().main) {
                "Clouds" -> itemView.forecast_ic_iv.setImageResource(R.drawable.art_clouds)
                "Rain" -> itemView.forecast_ic_iv.setImageResource(R.drawable.art_rain)
                "Snow" -> itemView.forecast_ic_iv.setImageResource(R.drawable.art_snow)
                "Clear" -> itemView.forecast_ic_iv.setImageResource(R.drawable.art_clear)
                "Fog" -> itemView.forecast_ic_iv.setImageResource(R.drawable.art_fog)
            }

            itemView.forecast_wind_speed_tv.text = "${item.wind.speed}"
            val displayPercentage = "${item.main.humidity}%"
            itemView.forecast_humidity_tv.text = displayPercentage
            val description =
                "${item.main.temp.toCelsiusOrFahrenheit()}, ${item.weather.first().description}"
            itemView.forecast_description_tv.text = description

            if (isMultipleDays) {
                itemView.forecast_day_tv.visibility = View.VISIBLE
                itemView.forecast_day_tv.text = item.dtText.stringToDate().getDayDisplay()
            } else itemView.forecast_day_tv.visibility = View.GONE
        }
    }
}



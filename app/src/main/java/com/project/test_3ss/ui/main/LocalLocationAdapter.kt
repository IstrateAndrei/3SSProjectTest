package com.project.test_3ss.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.test_3ss.R
import com.project.test_3ss.data.models.realmModels.LocationRealmModel
import com.project.test_3ss.utils.toCelsiusOrFahrenheit
import kotlinx.android.synthetic.main.local_location_item_layout.view.*

class LocalLocationAdapter(var localLocations: MutableList<LocationRealmModel>) :
    RecyclerView.Adapter<LocalLocationAdapter.LocalViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.local_location_item_layout, parent, false)
        return LocalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return localLocations.size
    }

    override fun onBindViewHolder(holder: LocalViewHolder, position: Int) {
        holder.displayData(localLocations[position])
    }

    inner class LocalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun displayData(item: LocationRealmModel) {
            when (item.weatherMain) {
                "Clouds" -> itemView.ll_icon_iv.setImageResource(R.drawable.art_clouds)
                "Rain" -> itemView.ll_icon_iv.setImageResource(R.drawable.art_rain)
                "Snow" -> itemView.ll_icon_iv.setImageResource(R.drawable.art_snow)
                "Clear" -> itemView.ll_icon_iv.setImageResource(R.drawable.art_clear)
                "Fog" -> itemView.ll_icon_iv.setImageResource(R.drawable.art_fog)
            }

            val description =
                "${item.temperature.toCelsiusOrFahrenheit()}, ${item.description}"
            itemView.ll_description_tv.text = description

            itemView.ll_location_tv.text = item.name
        }
    }

    fun updateList(locationList: MutableList<LocationRealmModel>) {
        localLocations.clear()
        localLocations.addAll(locationList)
        notifyDataSetChanged()
    }
}
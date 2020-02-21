package com.project.test_3ss.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.test_3ss.R
import com.project.test_3ss.data.models.LocalLocationModel
import com.project.test_3ss.utils.toCelsiusOrFahrenheit
import kotlinx.android.synthetic.main.local_location_item_layout.view.*

class LocalLocationAdapter(var localLocations: MutableList<LocalLocationModel>, var listener: LocalLocationListener) :
    RecyclerView.Adapter<LocalLocationAdapter.LocalViewHolder>() {

    interface LocalLocationListener{
        fun onLocationClick(locationId: Int)
    }

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

    fun updateList(locationList: MutableList<LocalLocationModel>) {
        localLocations.clear()
        localLocations.addAll(locationList)
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class LocalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun displayData(item: LocalLocationModel) {
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

            itemView.setOnClickListener {
                listener.onLocationClick(item.id)
            }
        }
    }
}
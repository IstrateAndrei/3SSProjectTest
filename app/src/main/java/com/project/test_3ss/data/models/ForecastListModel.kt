package com.project.test_3ss.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class ForecastListModel(
    @SerializedName("dt") var dt: Long = 0,
    @SerializedName("main") var main: MainModel = MainModel(),
    @SerializedName("weather") var weather: MutableList<WeatherModel> = mutableListOf(),
    @SerializedName("clouds") var clouds: CloudModel = CloudModel(),
    @SerializedName("wind") var wind: WindModel = WindModel(),
    @SerializedName("sys") var sys: SysModel = SysModel(),
    @SerializedName("dt_text") var dtText: Date = Date()
) : Parcelable {
}
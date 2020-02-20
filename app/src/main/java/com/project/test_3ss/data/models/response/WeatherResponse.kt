package com.project.test_3ss.data.models.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.project.test_3ss.data.models.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherResponse(
    @SerializedName("coord") var coord: CoordModel = CoordModel(),
    @SerializedName("weather") var weather: MutableList<WeatherModel> = mutableListOf(),
    @SerializedName("base") var base: String = "",
    @SerializedName("main") var main: MainModel = MainModel(),
    @SerializedName("visibility") var visibility: Int = 0,
    @SerializedName("wind") var wind: WindModel = WindModel(),
    @SerializedName("clouds") var cloudPercentage: CloudModel = CloudModel(),
    @SerializedName("dt") var dt: Long = 0,
    @SerializedName("sys") var sys: SysModel = SysModel(),
    @SerializedName("timezone") var timezone: Long = 0,
    @SerializedName("id") var id: Long = 0,
    @SerializedName("name") var name: String = "",
    @SerializedName("cod") var cod: Int = 0
) : Parcelable {
}
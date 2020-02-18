package com.project.test_3ss.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MainModel(
    @SerializedName("temp") var temp: Double = 0.0,
    @SerializedName("feels_like") var feelsLike: Double = 0.0,
    @SerializedName("temp_min") var temp_min: Double = 0.0,
    @SerializedName("temp_max") var temp_max: Double = 0.0,
    @SerializedName("pressure") var pressure: Int = 0,
    @SerializedName("humidity") var humidity: Int = 0
) : Parcelable {
}
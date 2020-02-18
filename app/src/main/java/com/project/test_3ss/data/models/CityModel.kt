package com.project.test_3ss.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CityModel(
    @SerializedName("id") var id: Long = 0,
    @SerializedName("name") var name: String = "",
    @SerializedName("coord") var coord: CoordModel = CoordModel(),
    @SerializedName("country") var country: String = "",
    @SerializedName("timezone") var timezone: Int = 0,
    @SerializedName("sunrise") var sunrise: Long = 0,
    @SerializedName("sunset") var sunset: Long = 0
) : Parcelable {
}
package com.project.test_3ss.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SysModel(
    @SerializedName("type") var type: Int = 0,
    @SerializedName("id") var id: Int = 0,
    @SerializedName("message") var message: Double = 0.0,
    @SerializedName("country") var country: String = "",
    @SerializedName("sunrise") var sunrise: Double = 0.0,
    @SerializedName("sunset") var sunset: Double = 0.0
) : Parcelable {
}
package com.project.test_3ss.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WindModel(
    @SerializedName("speed") var speed: Double = 0.0,
    @SerializedName("deg") var deg: Int = 0
) :Parcelable {
}
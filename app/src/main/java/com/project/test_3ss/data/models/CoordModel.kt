package com.project.test_3ss.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoordModel(
    @SerializedName("lon") var longitude: Double = 0.0,
    @SerializedName("lat") var latitude: Double = 0.0
) : Parcelable {

}
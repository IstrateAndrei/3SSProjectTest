package com.project.test_3ss.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CloudModel(
    @SerializedName("all") var all: Int = 0
) : Parcelable {
}
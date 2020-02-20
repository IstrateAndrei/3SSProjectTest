package com.project.test_3ss.data.models.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.project.test_3ss.data.models.CityModel
import com.project.test_3ss.data.models.ForecastListModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForecastResponse(
    @SerializedName("cod") var cod: String = "",
    @SerializedName("message") var message: Int = 0,
    @SerializedName("cnt") var cnt: Int = 0,
    @SerializedName("list") var list: MutableList<ForecastListModel> = mutableListOf(),
    @SerializedName("city") var city: CityModel = CityModel()
) : Parcelable {
}
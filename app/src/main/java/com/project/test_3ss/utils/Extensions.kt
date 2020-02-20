package com.project.test_3ss.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.orhanobut.hawk.Hawk
import java.text.SimpleDateFormat
import java.util.*


fun Double.toCelsiusOrFahrenheit(): String {
    if (Hawk.get<Boolean>(Constants.HAWK_IS_FAHRENHEIT_KEY) != null) {
        val isFahrenheit = Hawk.get<Boolean>(Constants.HAWK_IS_FAHRENHEIT_KEY)
        if (isFahrenheit) return "${((this - 273.15) * 9 / 5 + 32).toInt()}°F"
    }
    return "${(this - 273.15).toInt()}°C"
}

fun Date.getHourMinDisplay(): String {
    val calendar = Calendar.getInstance()
    calendar.time = this

    val hourDisplay =
        if (calendar.get(Calendar.HOUR_OF_DAY) < 10) "0${calendar.get(Calendar.HOUR_OF_DAY)}"
        else "${calendar.get(Calendar.HOUR_OF_DAY)}"

    val minuteDisplay = if (calendar.get(Calendar.MINUTE) < 10) "0${calendar.get(Calendar.MINUTE)}"
    else "${calendar.get(Calendar.MINUTE)}"

    return "$hourDisplay:$minuteDisplay"
}

@SuppressLint("SimpleDateFormat")
fun Date.getDayDisplay(): String {
    return SimpleDateFormat("EEE").format(this)
}

fun Long.getTimeFromUnix(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"
}

fun String.filterTodayForecast(): Boolean {
    val calendar = Calendar.getInstance()
    calendar.time = this.stringToDate()
    return calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
}

fun String.filterTomorrowForecast(): Boolean {
    val calendar = Calendar.getInstance()
    calendar.time = this.stringToDate()
    return calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR) + 1
}

fun String.filterNextDaysForecast(): Boolean {
    val calendar = Calendar.getInstance()
    calendar.time = this.stringToDate()
    return calendar.get(Calendar.DAY_OF_YEAR) > Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
}

@SuppressLint("SimpleDateFormat")
fun String.stringToDate(): Date {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return try {
        formatter.parse(this)
    } catch (e: Exception) {
        e.printStackTrace()
        Date()
    }
}

@SuppressLint("MissingPermission")
fun Activity.getLastKnownLocation(): Location {
    var location =
        (this.getSystemService(Context.LOCATION_SERVICE) as LocationManager).getLastKnownLocation(
            LocationManager.NETWORK_PROVIDER
        )
    if (location == null) location =
        (this.getSystemService(Context.LOCATION_SERVICE) as LocationManager).getLastKnownLocation(
            LocationManager.GPS_PROVIDER
        )
    return location
}

interface OneButtonCallback {
    fun buttonPressed()
}

fun defaultOneButtonDialog(
    context: Context,
    isCancelable: Boolean,
    dialogTitle: String,
    dialogMessage: String,
    buttonText: String,
    callback: OneButtonCallback
) {

    val builder = AlertDialog.Builder(context)
    builder.setCancelable(isCancelable)
        .setTitle(dialogTitle)
        .setMessage(dialogMessage)
        .setPositiveButton(buttonText) { dialog, _ ->
            dialog.dismiss()
            callback.buttonPressed()
        }

    val mDialog = builder.create()
    mDialog.show()
}

package com.project.test_3ss.utils

import com.mcxiaoke.koi.ext.round


fun Double.kelvinToCelsius(): Int{
    return (this - 273.15).toInt()
}

fun Double.kelvinToFahrenheit(): Int{
    return ((this - 273.15) * 9/5 + 32).toInt()
}
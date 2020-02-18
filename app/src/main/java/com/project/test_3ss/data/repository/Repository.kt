package com.project.test_3ss.data.repository

import com.project.test_3ss.data.models.response.ForecastResponse
import com.project.test_3ss.data.models.response.WeatherResponse
import com.project.test_3ss.data.network.RemoteDataSource
import io.reactivex.Observable
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class Repository : KoinComponent {

    private val remoteSource by inject<RemoteDataSource>()

    fun getRemoteLocationWeather(location: String): Observable<WeatherResponse> {
        return remoteSource.getLocationWeather(location)
    }

    fun getRemoteCurrentLocationWeather(
        latitude: Double,
        longitude: Double
    ): Observable<WeatherResponse> {
        return remoteSource.getCurrentLocationWeather(latitude, longitude)
    }

    fun getRemoteLocationForecastById(cityId: Int): Observable<ForecastResponse> {
        return remoteSource.getLocationForecastById(cityId)
    }

    fun getRemoteForecastByLocation(
        latitude: Double,
        longitude: Double
    ): Observable<ForecastResponse> {
        return remoteSource.getForecastByLocation(latitude, longitude)
    }
}
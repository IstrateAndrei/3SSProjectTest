package com.project.test_3ss.data.repository

import com.project.test_3ss.data.local.LocalDataSource
import com.project.test_3ss.data.models.WeatherModel
import com.project.test_3ss.data.models.realmModels.LocationRealmModel
import com.project.test_3ss.data.models.response.ForecastResponse
import com.project.test_3ss.data.models.response.WeatherResponse
import com.project.test_3ss.data.network.RemoteDataSource
import io.reactivex.Observable
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class Repository : KoinComponent {

    private val remoteSource by inject<RemoteDataSource>()
    private val localSource by inject<LocalDataSource>()

    //Remote data methods
    fun getRemoteLocationWeather(location: String): Observable<WeatherResponse> {
        return remoteSource.getLocationWeather(location)
    }

    fun getRemoteCurrentLocationWeather(
        latitude: Double,
        longitude: Double
    ): Observable<WeatherResponse> {
        return remoteSource.getCurrentLocationWeather(latitude, longitude)
    }

    fun getRemoteForecastByName(name: String): Observable<ForecastResponse> {
        return remoteSource.getForecastByName(name)
    }

    fun getRemoteLocationForecastById(cityId: Int): Observable<ForecastResponse> {
        return remoteSource.getLocationForecastById(cityId)
    }

    fun getRemoteWeatherById(locationId: Int): Observable<WeatherResponse> {
        return remoteSource.getWeatherById(locationId)
    }

    fun getRemoteForecastByLocation(
        latitude: Double,
        longitude: Double
    ): Observable<ForecastResponse> {
        return remoteSource.getForecastByLocation(latitude, longitude)
    }


    //Local data methods
    fun saveLocalLocation(location: LocationRealmModel) {
        localSource.saveLocalLocation(location)
    }

    fun removeLocalLocation(locationName: String) {
        localSource.removeLocalLocation(locationName)
    }

    fun getLocalLocations(): MutableList<LocationRealmModel> {
        return localSource.getLocalLocations()
    }

    fun isLocationSaved(location: WeatherResponse): Boolean {
        return localSource.isLocationSaved(location.id.toInt())
    }
}
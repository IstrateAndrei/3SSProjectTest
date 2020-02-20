package com.project.test_3ss.data.network

import com.project.test_3ss.data.models.response.ForecastResponse
import com.project.test_3ss.data.models.response.WeatherResponse
import com.project.test_3ss.utils.Constants
import io.reactivex.Observable
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class RemoteDataSource : KoinComponent {

    private val api by inject<ApiInterface>()

    fun getLocationWeather(location: String): Observable<WeatherResponse> {
        return api.getLocationWeather(location, Constants.API_KEY)
    }

    fun getCurrentLocationWeather(
        latitude: Double,
        longitude: Double
    ): Observable<WeatherResponse> {
        return api.getCurrentLocationWeather(latitude, longitude, Constants.API_KEY)
    }

    fun getForecastByName(name: String): Observable<ForecastResponse> {
        return api.getForecastByName(name, Constants.API_KEY)
    }

    fun getLocationForecastById(cityId: Int): Observable<ForecastResponse> {
        return api.getLocationForecastById(cityId, Constants.API_KEY)
    }

    fun getForecastByLocation(latitude: Double, longitude: Double): Observable<ForecastResponse> {
        return api.getForecastByLocation(latitude, longitude, Constants.API_KEY)
    }
}
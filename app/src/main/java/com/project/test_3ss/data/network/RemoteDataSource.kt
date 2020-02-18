package com.project.test_3ss.data.network

import com.project.test_3ss.data.models.response.ForecastResponse
import com.project.test_3ss.data.models.response.WeatherResponse
import com.project.test_3ss.utils.ApiConstants
import io.reactivex.Observable
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class RemoteDataSource : KoinComponent {

    private val api by inject<ApiInterface>()

    fun getLocationWeather(location: String): Observable<WeatherResponse> {
        return api.getLocationWeather(location, ApiConstants.API_KEY)
    }

    fun getCurrentLocationWeather(latitude: Double, longitude: Double): Observable<WeatherResponse> {
        return api.getCurrentLocationWeather(latitude, longitude, ApiConstants.API_KEY)
    }

    fun getLocationForecastById(cityId: Int) :Observable<ForecastResponse>{
        return  api.getLocationForecastById(cityId, ApiConstants.API_KEY)
    }

    fun getForecastByLocation(latitude: Double, longitude: Double) : Observable<ForecastResponse>{
        return api.getForecastByLocation(latitude, longitude, ApiConstants.API_KEY)
    }
}
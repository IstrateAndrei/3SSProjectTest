package com.project.test_3ss.data.network


import com.project.test_3ss.data.models.response.ForecastResponse
import com.project.test_3ss.data.models.response.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/data/2.5/weather")
    fun getLocationWeather(
        @Query("q") location: String,
        @Query("appid") apiKey: String
    ): Observable<WeatherResponse>

    @GET("/data/2.5/weather")
    fun getCurrentLocationWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ): Observable<WeatherResponse>

    //get 5 days/3 hour by city ID
    @GET("/data/2.5/forecast")
    fun getLocationForecastById(
        @Query("id") cityId: Int,
        @Query("appid") apiKey: String
    ) : Observable<ForecastResponse>

    @GET("/data/2.5/forecast")
    fun getForecastByLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ): Observable<ForecastResponse>
}
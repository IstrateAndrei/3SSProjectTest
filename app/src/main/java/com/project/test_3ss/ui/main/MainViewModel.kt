package com.project.test_3ss.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.project.test_3ss.data.models.response.ForecastResponse
import com.project.test_3ss.data.models.response.WeatherResponse
import com.project.test_3ss.data.repository.Repository
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class MainViewModel : ViewModel(), KoinComponent {

    private val repository by inject<Repository>()
    private val _index = MutableLiveData<Int>()

    val page: LiveData<Int> = Transformations.map(_index) { it }

    val currentLocationObservable: MutableLiveData<WeatherResponse> = MutableLiveData()
    val currentForecastObservable: MutableLiveData<ForecastResponse> = MutableLiveData()
    val savedLocationsObservable: MutableLiveData<List<WeatherResponse>> = MutableLiveData()

    fun setIndex(index: Int) {
        _index.value = index
    }

    fun getLocationWeather(location: String) {
        repository.getRemoteLocationWeather(location)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<WeatherResponse> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: WeatherResponse) {

                }

                override fun onError(e: Throwable) {

                }

            })
    }

    fun getLocationForecastById(cityId: Int) {
        repository.getRemoteLocationForecastById(cityId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ForecastResponse> {
                override fun onComplete() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onSubscribe(d: Disposable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onNext(t: ForecastResponse) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onError(e: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
    }


    fun getCurrentLocationWeather(latitude: Double, longitude: Double) {
        repository.getRemoteCurrentLocationWeather(latitude, longitude)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<WeatherResponse> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: WeatherResponse) {
                    currentLocationObservable.value = t
                }

                override fun onError(e: Throwable) {
                    Log.e("Debug", e.message)
                }

            })
    }

    fun getCurrentForecast(latitude: Double, longitude: Double){
        repository.getRemoteForecastByLocation(latitude, longitude)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ForecastResponse>{
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: ForecastResponse) {
                    currentForecastObservable.value = t
                }

                override fun onError(e: Throwable) {

                }

            })
    }


}
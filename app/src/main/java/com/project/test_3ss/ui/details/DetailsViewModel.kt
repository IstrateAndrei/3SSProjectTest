package com.project.test_3ss.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.test_3ss.data.models.realmModels.LocationRealmModel
import com.project.test_3ss.data.models.response.ForecastResponse
import com.project.test_3ss.data.models.response.WeatherResponse
import com.project.test_3ss.data.repository.Repository
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class DetailsViewModel : ViewModel(), KoinComponent {

    private val repository by inject<Repository>()
    val forecastObservable: MutableLiveData<ForecastResponse> = MutableLiveData()
    val errorObservable: MutableLiveData<Throwable> = MutableLiveData()
    val weatherObservable: MutableLiveData<WeatherResponse> = MutableLiveData()

    fun getWeatherById(locationId: Int) {
        repository.getRemoteWeatherById(locationId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<WeatherResponse> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: WeatherResponse) {
                    weatherObservable.value = t
                }

                override fun onError(e: Throwable) {
                    errorObservable.value = e
                }

            })
    }

    fun getCurrentWeather(latitude: Double, longitude: Double) {
        repository.getRemoteCurrentLocationWeather(latitude, longitude)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<WeatherResponse> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: WeatherResponse) {
                    weatherObservable.value = t
                }

                override fun onError(e: Throwable) {
                    errorObservable.value = e
                }

            })
    }

    fun getForecastById(cityId: Int) {
        repository.getRemoteLocationForecastById(cityId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ForecastResponse> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: ForecastResponse) {
                    forecastObservable.value = t
                }

                override fun onError(e: Throwable) {
                    errorObservable.value = e
                }

            })
    }

    fun checkIfLocationSaved(location: WeatherResponse): Boolean {
        return repository.isLocationSaved(location)
    }

    fun saveLocalLocation(location: LocationRealmModel) {
        repository.saveLocalLocation(location)
    }

    fun removeLocalLocation(locationName: String) {
        repository.removeLocalLocation(locationName)
    }

    //todo make generic Observer
}
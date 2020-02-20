package com.project.test_3ss.ui.search

import androidx.lifecycle.MutableLiveData
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

class SearchViewModel : ViewModel(), KoinComponent {

    private val repository by inject<Repository>()

    val locationDetailsObservable: MutableLiveData<WeatherResponse> = MutableLiveData()
    val locationErrorObservable: MutableLiveData<Throwable> = MutableLiveData()

    fun getWeatherByName(location: String) {
        repository.getRemoteLocationWeather(location)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<WeatherResponse> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: WeatherResponse) {
                    locationDetailsObservable.value = t
                }

                override fun onError(e: Throwable) {
                    locationErrorObservable.value = e
                }

            })
    }

}
package com.project.test_3ss.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.project.test_3ss.data.models.LocalLocationModel
import com.project.test_3ss.data.models.WeatherModel
import com.project.test_3ss.data.models.realmModels.LocationRealmModel
import com.project.test_3ss.data.models.response.ForecastResponse
import com.project.test_3ss.data.models.response.WeatherResponse
import com.project.test_3ss.data.repository.Repository
import com.project.test_3ss.utils.toLocalLocations
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class MainViewModel : ViewModel(), KoinComponent {

    private val repository by inject<Repository>()
    private val _index = MutableLiveData<Int>()

    val page: LiveData<Int> = Transformations.map(_index) { it }
    val savedLocationsObservable: MutableLiveData<MutableList<LocalLocationModel>> = MutableLiveData()

    fun getLocalLocations() {
        savedLocationsObservable.value = repository.getLocalLocations().toLocalLocations()
    }

    fun fetchLocalLocations(): MutableList<LocalLocationModel>{
        return repository.getLocalLocations().toLocalLocations()
    }

}
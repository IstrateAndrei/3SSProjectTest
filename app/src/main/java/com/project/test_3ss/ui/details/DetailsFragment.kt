package com.project.test_3ss.ui.details

import android.Manifest
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.project.test_3ss.R
import com.project.test_3ss.data.models.ForecastListModel
import com.project.test_3ss.data.models.realmModels.LocationRealmModel
import com.project.test_3ss.data.models.response.ForecastResponse
import com.project.test_3ss.data.models.response.WeatherResponse
import com.project.test_3ss.ui.main.ForecastAdapter
import com.project.test_3ss.utils.*
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.location_details_layout.*

class DetailsFragment : Fragment() {

    private lateinit var detailsViewModel: DetailsViewModel
    private val mDisposable = CompositeDisposable()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var locationId = 0
    private var isFavorite = false
    private var isToday = false
    private var is16Days = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.location_details_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsViewModel = ViewModelProvider(requireActivity()).get(DetailsViewModel::class.java)
        observe()
        initListeners()
        if (arguments == null) requestLocationAccess()
        else {
            arguments?.let { bundle ->
                val weather =
                    bundle.getParcelable<WeatherResponse>(Constants.BUNDLE_WEATHER_RESPONSE_KEY)
                isFavorite = bundle.getBoolean(Constants.BUNDLE_IS_FAVOURITE_KEY)
                weather?.let {
                    detailsViewModel.weatherObservable.value = it
                    return
                }

                val locationId = bundle.getInt(Constants.BUNDLE_LOCATION_ID_KEY)
                detailsViewModel.getWeatherById(locationId)
            }
        }
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
            p0?.let {
                detailsViewModel.getCurrentWeather(
                    it.lastLocation.latitude,
                    it.lastLocation.longitude
                )
            }
        }
    }

    private fun requestLocationAccess() {
        val disposable = RxPermissions(requireActivity())
            .requestEachCombined(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .subscribe { permission ->
                if (permission.granted) {
                    //check if location is provided
                    if ((requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(
                            LocationManager.GPS_PROVIDER
                        ) ||
                        (requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(
                            LocationManager.NETWORK_PROVIDER
                        )
                    ) {
                        fusedLocationClient =
                            LocationServices.getFusedLocationProviderClient(requireActivity())
                        fusedLocationClient.requestLocationUpdates(
                            LocationRequest().setInterval(
                                10000 * 60  // refresh current location weather data time span 10 min
                            ), mLocationCallback, Looper.myLooper()
                        )
                    }
                } else
                    mf_location_warning_tv.visibility = View.VISIBLE
            }
        mDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable.clear()
    }

    private fun showForecast(forecast: ForecastResponse) {

        if (cl_forecast_rv.layoutManager == null)
            cl_forecast_rv.layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        val resultList: List<ForecastListModel>

        resultList = if (isToday) forecast.list.filter { item -> item.dtText.filterTodayForecast() }
        else {
            if (is16Days) forecast.list.filter { item -> item.dtText.filterNextDaysForecast() }
            else forecast.list.filter { item -> item.dtText.filterTomorrowForecast() }
        }

        if (cl_forecast_rv.adapter == null) cl_forecast_rv.adapter =
            ForecastAdapter(resultList.toMutableList(), is16Days)
        else (cl_forecast_rv.adapter as ForecastAdapter).updateList(
            resultList.toMutableList(),
            is16Days
        )
    }

    private fun displayWeatherInfo(weather: WeatherResponse) {
        val description =
            "${weather.name} ${weather.main.temp.toCelsiusOrFahrenheit()}, ${weather.weather.first().description}"
        cl_description_tv.text = description

        cl_save_cb.isChecked = detailsViewModel.checkIfLocationSaved(weather)
        locationId = weather.id.toInt()

        if (isFavorite) {
            detailsViewModel.saveLocalLocation(weather.weatherToRealm())
            cl_save_cb.isChecked = true
        }

    }

    private val forecastObserver = Observer<ForecastResponse> {
        showForecast(it)
    }

    private val weatherObserver = Observer<WeatherResponse> {
        displayWeatherInfo(it)
    }

    private val errorObserver = Observer<Throwable> {
        it.printStackTrace()
        defaultOneButtonDialog(requireContext(), false,
            resources.getString(R.string.error_title_string),
            resources.getString(R.string.error_message_string),
            resources.getString(R.string.ok_string), object : OneButtonCallback {
                override fun buttonPressed() {

                }
            })
    }

    private fun observe() {
        detailsViewModel.forecastObservable.observe(viewLifecycleOwner, forecastObserver)
        detailsViewModel.weatherObservable.observe(viewLifecycleOwner, weatherObserver)
        detailsViewModel.errorObservable.observe(viewLifecycleOwner, errorObserver)
    }

    private fun initListeners() {
        cl_today_rb.setOnCheckedChangeListener { _, b ->
            if (b) {
                isToday = true
                is16Days = false
                detailsViewModel.getForecastById(locationId)
            }
            return@setOnCheckedChangeListener
        }

        cl_tomorrow_rb.setOnCheckedChangeListener { _, b ->
            if (b) {
                isToday = false
                is16Days = false
                detailsViewModel.getForecastById(locationId)
            }
            return@setOnCheckedChangeListener
        }

        cl_16day_rb.setOnCheckedChangeListener { _, b ->
            if (b) {
                isToday = false
                is16Days = true
                Toast.makeText(
                    requireContext(),
                    R.string.not_available_string,
                    Toast.LENGTH_SHORT
                ).show()
            }
            detailsViewModel.getForecastById(locationId)
        }

        cl_save_cb.setOnCheckedChangeListener { _, b ->
            if (b) {
                detailsViewModel.weatherObservable.value?.let {
                    detailsViewModel.saveLocalLocation(it.weatherToRealm())
                }
            } else {
                detailsViewModel.weatherObservable.value?.let {
                    detailsViewModel.removeLocalLocation(it.name)
                }
            }
        }

        cl_refresh_iv.setOnClickListener {
            detailsViewModel.getForecastById(locationId)
        }
    }
}
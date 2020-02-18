package com.project.test_3ss.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.text.format.Time
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.*
import com.project.test_3ss.R
import com.project.test_3ss.data.models.response.ForecastResponse
import com.project.test_3ss.data.models.response.WeatherResponse
import com.project.test_3ss.utils.kelvinToCelsius
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.current_location_layout.*
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*

class MainFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private val mDisposable = CompositeDisposable()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val currentLocationObserver = Observer<WeatherResponse> { currentLocationWeather ->
        currentLocationWeather?.let {
            cl_layout.visibility = View.VISIBLE
            val description = //todo handle Celsius Fahrenheit degrees
                "${it.name} - ${it.main.temp.kelvinToCelsius()}${R.string.degree_symbol_string}C, ${it.weather.first().description}"
            cl_description_tv.text = description
        }
    }

    private val currentForecastObserver = Observer<ForecastResponse> { currentForecastResponse ->
        currentForecastResponse?.let {
            cl_layout.visibility = View.VISIBLE
            //todo filter tomorrow forecast

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        @JvmStatic
        fun newInstance(sectionNumber: Int): MainFragment {
            return MainFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        initListeners()
    }

    @SuppressLint("MissingPermission")
    private fun initListeners() {

        val lat =
            (requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager).getLastKnownLocation(
                LocationManager.NETWORK_PROVIDER
            ).latitude
        val lon =
            (requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager).getLastKnownLocation(
                LocationManager.NETWORK_PROVIDER
            ).longitude

        mf_location_warning_tv.setOnClickListener { view ->
            mainViewModel.page.value?.let {
                handlePage(it)
                view.visibility = View.GONE
            }
        }
        cl_today_rb.setOnCheckedChangeListener { _, b ->
            if (b) mainViewModel.getCurrentLocationWeather(lat, lon)
            return@setOnCheckedChangeListener
        }

        cl_tomorrow_rb.setOnCheckedChangeListener { _, b ->
            if (b) mainViewModel.getCurrentForecast(lat, lon)
            return@setOnCheckedChangeListener
        }

        cl_16day_rb.setOnCheckedChangeListener { _, b ->
            if (b) Toast.makeText(
                requireContext(),
                R.string.not_available_string,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun observe() {
        mainViewModel.page.observe(viewLifecycleOwner, Observer<Int> {
            handlePage(it)
        })
        mainViewModel.currentLocationObservable.observe(viewLifecycleOwner, currentLocationObserver)
        mainViewModel.currentForecastObservable.observe(
            viewLifecycleOwner,
            currentForecastObserver
        )
    }

    private fun handlePage(page: Int) {
        when (page) {
            1 -> {
                // todo show saved locations
            }
            2 -> {
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
        }
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
            p0?.let {
                mainViewModel.getCurrentLocationWeather(
                    it.lastLocation.latitude,
                    it.lastLocation.longitude
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable.clear()
    }


}
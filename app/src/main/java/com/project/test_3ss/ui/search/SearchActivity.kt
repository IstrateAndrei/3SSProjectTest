package com.project.test_3ss.ui.search

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.orhanobut.hawk.Hawk
import com.project.test_3ss.R
import com.project.test_3ss.data.models.response.WeatherResponse
import com.project.test_3ss.ui.details.DetailsFragment
import com.project.test_3ss.ui.main.MainActivity
import com.project.test_3ss.utils.Constants
import com.project.test_3ss.utils.OneButtonCallback
import com.project.test_3ss.utils.defaultOneButtonDialog
import kotlinx.android.synthetic.main.search_activity_layout.*

class SearchActivity : AppCompatActivity() {

    private lateinit var searchViewModel: SearchViewModel
    private var isFavourite = false

    private val locationDetailsObserver = Observer<WeatherResponse> {
        //open location details screen and bundle the data
        val bundle = Bundle()
        bundle.putParcelable(Constants.BUNDLE_WEATHER_RESPONSE_KEY, it)
        bundle.putBoolean(Constants.BUNDLE_IS_FAVOURITE_KEY, isFavourite)
        val fragment = DetailsFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.ld_frame_container, fragment)
            .addToBackStack(DetailsFragment::class.java.simpleName).commit()
    }

    private val locationErrorObserver = Observer<Throwable> {
        //show no location found
        defaultOneButtonDialog(this,
            true,
            resources.getString(R.string.invalid_location_string),
            resources.getString(R.string.invalid_location_message_string),
            resources.getString(
                R.string.ok_string
            ),
            object : OneButtonCallback {
                override fun buttonPressed() {
                    search_location_et.text.clear()
                }
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        setContentView(R.layout.search_activity_layout)
        initListeners()
        observe()

        intent.extras?.let{
            val locationId = it.getInt(Constants.INTENT_LOCATION_ID_KEY)
            openLocationDetails(locationId)
        }
    }

    private fun openLocationDetails(locationId: Int){
        val bundle = Bundle()
        bundle.putInt(Constants.BUNDLE_LOCATION_ID_KEY, locationId)
        val fragment = DetailsFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.ld_frame_container, fragment)
            .addToBackStack(DetailsFragment::class.java.simpleName).commit()
    }

    private fun observe() {
        searchViewModel.locationErrorObservable.observe(this, locationErrorObserver)
        searchViewModel.locationDetailsObservable.observe(this, locationDetailsObserver)
    }

    private fun initListeners() {

        Hawk.get<Boolean>(Constants.HAWK_IS_FAHRENHEIT_KEY)?.let {
            degree_switch.isChecked = it
        }
        degree_switch.setOnCheckedChangeListener { _, b ->
            if (b) Hawk.put(Constants.HAWK_IS_FAHRENHEIT_KEY, true)
            else Hawk.put(Constants.HAWK_IS_FAHRENHEIT_KEY, false)
        }

        favourite_switch.setOnCheckedChangeListener { compoundButton, b ->
            isFavourite = b
        }

        view_weather_btn.setOnClickListener {
            if (search_location_et.text.isEmpty()) {
                Toast.makeText(
                    this,
                    R.string.fill_location_field_warning_string,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            searchViewModel.getWeatherByName(search_location_et.text.toString())

        }

        location_img.setOnClickListener {
            //todo redirect to current location page
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(Constants.INTENT_CURRENT_LOCATION_REDIRECT_KEY, true)
            startActivity(intent)
            this.finish()
        }

        back_button_iv.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }
    }

}
package com.project.test_3ss.ui.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.project.test_3ss.R
import com.project.test_3ss.data.models.LocalLocationModel
import com.project.test_3ss.data.models.realmModels.LocationRealmModel
import com.project.test_3ss.ui.details.DetailsFragment
import com.project.test_3ss.ui.search.SearchActivity
import com.project.test_3ss.utils.Constants
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.local_locations_layout.*

class MainFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        observe()
        initListeners()
        (activity as MainActivity).setupPagerCallback(pagerCallback)
        mainViewModel.getLocalLocations()
    }

    private val localLocationListener = object : LocalLocationAdapter.LocalLocationListener {
        override fun onLocationClick(locationId: Int) {
            val intent = Intent(activity, SearchActivity::class.java)
            intent.putExtra(Constants.INTENT_LOCATION_ID_KEY, locationId)
            startActivity(intent)
            activity?.finish()
        }
    }

    private val localLocationsObserver = Observer<MutableList<LocalLocationModel>> {
        if (it.isNotEmpty()) {
            if (local_location_rv.layoutManager == null) local_location_rv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            if (local_location_rv.adapter == null) {
                local_location_rv.adapter = LocalLocationAdapter(it, localLocationListener)
            } else (local_location_rv.adapter as LocalLocationAdapter).updateList(it)

        } else {
            local_location_rv.visibility = View.GONE
            local_location_et.visibility = View.GONE
        }
    }

    private val pagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (position == 0) {
                mainViewModel.getLocalLocations()
            }
        }
    }

    private fun initListeners() {
        mf_location_warning_tv.setOnClickListener { view ->
            mainViewModel.page.value?.let {
                view.visibility = View.GONE
            }
        }

        local_location_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let {
                    if (it.length >= 2) {
                        val filteredList =
                            mainViewModel.fetchLocalLocations().filter { item ->
                                item.name.contains(it)
                            }
                        (local_location_rv.adapter as LocalLocationAdapter).updateList(filteredList.toMutableList())
                    } else {
                        (local_location_rv.adapter as LocalLocationAdapter).updateList(mainViewModel.fetchLocalLocations())
                    }
                }
            }
        })
    }

    private fun observe() {
        mainViewModel.savedLocationsObservable.observe(viewLifecycleOwner, localLocationsObserver)
    }
}


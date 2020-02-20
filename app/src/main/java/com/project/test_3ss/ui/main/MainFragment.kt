package com.project.test_3ss.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.test_3ss.R
import com.project.test_3ss.data.models.realmModels.LocationRealmModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.local_locations_layout.*

class MainFragment : Fragment(), UpdateListener {

    private lateinit var mainViewModel: MainViewModel

    private val localLocationsObserver = Observer<MutableList<LocationRealmModel>> {
        if (it.isNotEmpty()) {
            if (local_location_rv.layoutManager == null) local_location_rv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            if (local_location_rv.adapter == null)
                local_location_rv.adapter = LocalLocationAdapter(it)
            else (local_location_rv.adapter as LocalLocationAdapter).updateList(it)
        }else {
            local_location_rv.visibility = View.GONE
            local_location_et.visibility = View.GONE
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
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        observe()
        initListeners()
        mainViewModel.getLocalLocations()
    }

    private fun initListeners() {
        mf_location_warning_tv.setOnClickListener { view ->
            mainViewModel.page.value?.let {
                view.visibility = View.GONE
            }
        }
    }

    private fun observe() {
        mainViewModel.savedLocationsObservable.observe(viewLifecycleOwner, localLocationsObserver)
    }

    override fun update() {
        mainViewModel.getLocalLocations()
    }
}

interface UpdateListener{
    fun update()
}
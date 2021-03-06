package com.project.test_3ss.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.common.api.internal.LifecycleCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.project.test_3ss.R
import com.project.test_3ss.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this)

        view_pager.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabs, view_pager) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.locations_tab_string)
                1 -> tab.text = resources.getString(R.string.current_location_tab_string)
            }
        }.attach()

        search_fab.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
            this.finish()
        }
    }

    fun refreshViewPager() {
        view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

            }
        })
    }

}
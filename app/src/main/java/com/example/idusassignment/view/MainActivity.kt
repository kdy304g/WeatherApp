package com.example.idusassignment.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.idusassignment.R
import com.example.idusassignment.adapter.WeatherAdapter
import com.example.idusassignment.databinding.ActivityMainBinding
import com.example.idusassignment.model.City
import com.example.idusassignment.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linearLayoutManager = LinearLayoutManager(applicationContext)
        weather_recycler_view.layoutManager =linearLayoutManager

        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainActivityViewModel.viewInit(this, binding.weatherRecyclerView)
        mainActivityViewModel.getCities(getString(R.string.query_string), this)

        binding.mainActivityViewModel = mainActivityViewModel
        binding.lifecycleOwner = this
        setupSwipeAction()
    }

    private fun setupSwipeAction(){
        swipe_recycler_view.setOnRefreshListener(this)
        swipe_recycler_view.setColorSchemeColors(
            resources.getColor(R.color.design_default_color_primary),
            resources.getColor(android.R.color.holo_green_dark),
            resources.getColor(android.R.color.holo_orange_dark),
            resources.getColor(android.R.color.holo_blue_dark))
        swipe_recycler_view.post {
            swipe_recycler_view.isRefreshing = true
            mainActivityViewModel.getCities(getString(R.string.query_string), this)
        }
    }

    override fun onRefresh() {
        mainActivityViewModel.getCities(getString(R.string.query_string), this)
    }
}
package com.example.idusassignment.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.idusassignment.R
import com.example.idusassignment.adapter.WeatherAdapter
import com.example.idusassignment.model.City
import com.example.idusassignment.model.Weather
import com.example.idusassignment.model.WeatherResponse
import io.reactivex.Observable
import com.example.idusassignment.repository.MainActivityRepository
import com.example.idusassignment.view.MainActivity
import java.util.*

class MainActivityViewModel: ViewModel() {
    private var cities: ArrayList<City> = arrayListOf()
    private var weatherAdapter = WeatherAdapter(this)
    @SuppressLint("StaticFieldLeak")
    private lateinit var loadingPanel: View
    private lateinit var swipePanel: SwipeRefreshLayout

    @SuppressLint("CheckResult")
    fun getCities(query: String, context: Context) {
        loadingPanel = (context as MainActivity).findViewById(R.id.loadingPanel)
        swipePanel = (context as MainActivity).findViewById(R.id.swipe_recycler_view)
        swipePanel.isRefreshing = true

        cities.clear()
        weatherAdapter.notifyDataSetChanged()
        MainActivityRepository.getCitiesApiCall(query)
            .flatMap { cities -> Observable.fromIterable(cities) }
            .flatMap { city -> MainActivityRepository.getCityWeatherApiCall(city.woeid) }
            .subscribe {
                val c = City(it.title)
                c.weathers = it.consolidatedWeather
                cities.add(c)
                Handler(Looper.getMainLooper()).post(){
                    weatherAdapter.notifyDataSetChanged()
                    loadingPanel.visibility = View.GONE
                    swipePanel.isRefreshing = false
                }
            }
    }

    fun getWeatherState(pos: Int, date: Int): String {
        return cities[pos - 1].weathers[date].weatherState
    }

    fun getWeatherStateAbbr(pos: Int, date: Int): String {
        return cities[pos - 1].weathers[date].weatherStateAbbr
    }

    fun getTemp(pos: Int, date: Int): String {
        return cities[pos - 1].weathers[date].temp.toInt().toString() + " \u2109"
    }

    fun getHumidity(pos: Int, date: Int): String {
        return cities[pos - 1].weathers[date].humidity.toInt().toString() + "%"
    }

    fun getCity(pos: Int): String {
        return cities[pos - 1].title
    }

    fun viewInit(context: Context, recyclerView: RecyclerView){
        recyclerView.adapter = weatherAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    fun getCityItems(): List<City>{
        return cities
    }

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String?) {
            if(!url.isNullOrBlank()){
                Glide.with(view.context).load("https://www.metaweather.com/static/img/weather/png/64/"+url+".png").into(view)
            }
        }
    }
}
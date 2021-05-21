package com.example.idusassignment.repository

import com.example.idusassignment.model.City
import com.example.idusassignment.model.WeatherResponse
import com.example.idusassignment.retrofit.RetrofitClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


object MainActivityRepository {

    fun getCitiesApiCall(query: String): Observable<List<City>> =
        RetrofitClient.apiInterface.getLocation(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getCityWeatherApiCall(woeid: Int): Observable<WeatherResponse> =
        RetrofitClient.apiInterface.getWeather(woeid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
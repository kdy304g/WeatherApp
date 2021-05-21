package com.example.idusassignment.retrofit

import com.example.idusassignment.model.City
import com.example.idusassignment.model.WeatherResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("search")
    fun getLocation(@Query("query") query: String): Observable<List<City>>

    @GET("{woeid}")
    fun getWeather(@Path("woeid") woeid: Int): Observable<WeatherResponse>
}
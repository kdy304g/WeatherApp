package com.example.idusassignment.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("consolidated_weather")
    val consolidatedWeather: List<Weather>,

    @SerializedName("title")
    val title: String

)

data class Weather(
    @SerializedName("weather_state_name")
    val weatherState: String,

    @SerializedName("weather_state_abbr")
    val weatherStateAbbr: String,

    @SerializedName("the_temp")
    val temp: Double,

    @SerializedName("humidity")
    val humidity: Int
)
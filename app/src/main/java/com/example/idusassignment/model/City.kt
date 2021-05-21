package com.example.idusassignment.model

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("title")
    val title: String,
){
    @SerializedName("location_type")
    val locationType: String = ""

    @SerializedName("woeid")
    var woeid: Int = 0

    @SerializedName("latt_long")
    val latLng: String = ""

    var weathers: List<Weather> = ArrayList<Weather>()
}

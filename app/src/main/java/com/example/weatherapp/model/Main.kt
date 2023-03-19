package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class Main(
// Here the humidity is determined with respect to groud level.
    @SerializedName("grnd_level")

    val grndLevel: Int,

    val humidity: Int,

// Here the wind speed is determined with respect to sea level.
    @SerializedName("sea_level")

    val seaLevel: Int,

    val temp: Double,

)
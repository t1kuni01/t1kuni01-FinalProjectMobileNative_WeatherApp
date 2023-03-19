package com.example.weatherapp.model

//Here is the all varaible is define in this weather model file.

data class WeatherModel(
    val base: String,

    val id: Int,

    val main: Main,

    val name: String,

    val sys: Sys,

    val weather: List<Weather>,

    val wind: Wind
)
package com.example.weatherapp.service


import com.example.weatherapp.model.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.Single

// API key from the data is processing and retrieving.

interface WeatherAPI {
    @GET("data/2.5/weather?&units=metric&APPID=840698d83dfe49c01a372ebe6c4b10e4")

// Get and Query methods.
    fun getData(
        @Query("q") cityName: String

    ): Single<WeatherModel>

}

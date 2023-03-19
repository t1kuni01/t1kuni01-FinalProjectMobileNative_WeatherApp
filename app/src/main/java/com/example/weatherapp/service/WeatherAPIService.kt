package com.example.weatherapp.service

import com.example.weatherapp.model.WeatherModel
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Single
import retrofit2.Retrofit


//Open WeatherAPIService from the data is processing and retrieving.


class WeatherAPIService {


    private val BASE_URL = "http://api.openweathermap.org/"



//By creating the Retrofit.Builder instance,the code sets various configuration options
// For example the base URL for the API, any required headers, or the type of data converter to use for parsing API responses.
    private val api = Retrofit.Builder()

        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

// Set code for getting data from open API.

    fun getDataService(cityName: String): Single<WeatherModel> {

        return api.getData(cityName)
    }


}
package com.example.weatherapp.view


import com.example.weatherapp.viewmodel.MainViewModel
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import com.example.weatherapp.R
import kotlin.math.roundToInt


private const val TAG = "MainActivity"


class MainActivity : AppCompatActivity() {

    private lateinit var viewmodel: MainViewModel

// Get and Set variables:

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()


        viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        val cName = GET.getString("locationName", "oulu")?.toLowerCase()
        edt_city_name.setText(cName)
        viewmodel.refreshData(cName!!)

        getLiveData()


        //setOnRefreshListener method is executed.
        // This can include actions such as updating the contents of a RecyclerView, reloading data from an API, or refreshing the current activity.
        swipe_refresh_layout.setOnRefreshListener {
            ll_data.visibility = View.GONE
            pb_loading.visibility = View.GONE
            tv_error.visibility = View.GONE


            // by GET function here we are getting the cityname.
            val cityName = GET.getString("locationName", cName)?.toLowerCase()
            edt_city_name.setText(cityName)
            viewmodel.refreshData(cityName!!)
            swipe_refresh_layout.isRefreshing = false
        }

       // setOnClickListerner is a method which is execute a new activity,displaying a dialog box, or performing a search based on the city name entered by the user.

        img_search_city.setOnClickListener {
            val cityName = edt_city_name.text.toString()
            SET.putString("locationName", cityName)
            SET.apply()
            viewmodel.refreshData(cityName)
            getLiveData()
            Log.i(TAG,"onCreate: " + cityName)
        }

    }


//The getLiveData() function may be called by other functions or methods in the app.
// To retrieve data and update the UI accordingly.

    private fun getLiveData() {

        viewmodel.weather_data.observe(this, Observer { data ->
            data?.let {
                ll_data.visibility = View.VISIBLE
                tv_city_code.text = data.sys.country.toString()
                tv_city_name.text = data.name.toString()
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.weather.get(0).icon + "@2x.png")
                    .into(img_weather_pictures)



                // Temperature in celsius and fahrenheit

                val temperature = data.main.temp
                val roundedTempC = temperature.roundToInt() // round off to nearest integer
                val roundedTempF = roundedTempC * 9/5 + 32
                tv_degree.text = "${roundedTempC}°C / ${roundedTempF}°F"



                 // Calculating the Humidity and Wind Speed.
                tv_wind_speed.text = data.wind.speed.toString()
                tv_humidity.text = data.main.humidity.toString() + "%"

            }
        })


    }
}
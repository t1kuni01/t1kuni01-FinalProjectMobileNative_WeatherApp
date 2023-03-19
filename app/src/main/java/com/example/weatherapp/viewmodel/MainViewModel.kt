package com.example.weatherapp.viewmodel



import com.example.weatherapp.service.WeatherAPIService
import com.example.weatherapp.model.WeatherModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.disposables.CompositeDisposable
import androidx.lifecycle.MutableLiveData
import io.reactivex.schedulers.Schedulers
import androidx.lifecycle.ViewModel
import android.util.Log



//The TAG constant is used as a unique identifier for a particular class or module in the app.
// That is used in log statements to provide context for the logged messages.
private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {
    private val disposable = CompositeDisposable()
    private val weatherApiService = WeatherAPIService()

    val weather_data = MutableLiveData<WeatherModel>()
    val weather_loading = MutableLiveData<Boolean>()
    val weather_error = MutableLiveData<Boolean>()

    fun refreshData(cityName: String) {
        getDataFromAPI(cityName)
    }


//The getDataFromAPI(cityName: String) function is a custom function that retrieves weather data for a specified city from a remote API.
    private fun getDataFromAPI(cityName: String) {
        weather_loading.value = true
        disposable.add(
            weatherApiService.getDataService(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherModel>() {




//This represents that the data retrieved from a weather API,
// Which may include information such as the current temperature, weather conditions, and other weather-related data.

                    override fun onSuccess(t: WeatherModel) {
                        weather_data.value = t
                        weather_loading.value = false
                        weather_error.value = false

                        Log.d(TAG, "onSuccess: Success")
                    }



// The onError() function may include logic for handling the error,
// For Example displaying an error message to the user or retrying the data retrieval operation after a certain amount of time.

                    override fun onError(e: Throwable) {
                        weather_loading.value = false
                        weather_error.value = true
                        Log.e(TAG, "onError: " + e)
                    }

                })
        )

    }

}
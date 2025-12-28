package com.example.keepfit.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse

    companion object {
        private var instance: WeatherApi? = null
        fun getInstance(): WeatherApi {
            return instance ?: synchronized(this) {
                instance ?: create().also { instance = it }
            }
        }

        fun create(): WeatherApi {
            val retrofit = retrofit2.Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                .build()
            return retrofit.create(WeatherApi::class.java)
        }
    }
}
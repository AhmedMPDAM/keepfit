package com.example.keepfit.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.keepfit.data.remote.WeatherApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val api = WeatherApi.create()
    private val _temperature = MutableStateFlow(0.0)
    val temperature: StateFlow<Double> = _temperature

    private val _suggestion = MutableStateFlow("")
    val suggestion: StateFlow<String> = _suggestion

    fun fetchWeather(city: String = "Tunis") {
        viewModelScope.launch {
            try {
                val response = api.getWeather(city, "486a5871a20d2546afeb50b1db1d904f")
                _temperature.value = response.main.temp
                _suggestion.value = when {
                    response.main.temp > 26 -> "Too hot, be careful"
                    response.main.temp < 16 -> "May be cold, expect rain"
                    else -> "Suitable for run"
                }
            } catch (e: Exception) {
                _suggestion.value = "Unable to fetch weather"
            }
        }
    }
}
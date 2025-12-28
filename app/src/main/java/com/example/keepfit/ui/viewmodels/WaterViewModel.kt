package com.example.keepfit.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.keepfit.data.local.entities.WaterIntake
import com.example.keepfit.data.repository.WaterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class WaterViewModel(private val repository: WaterRepository) : ViewModel() {
    private val _waterAmount = MutableStateFlow(0)
    val waterAmount: StateFlow<Int> = _waterAmount

    fun loadTodayWater(userId: Int) {
        viewModelScope.launch {
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val intake = repository.getTodayIntake(userId, today)
            _waterAmount.value = intake?.amountMl ?: 0
        }
    }

    fun addWater(userId: Int, amount: Int) {
        viewModelScope.launch {
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val current = repository.getTodayIntake(userId, today)

            if (current != null) {
                val updated = current.copy(amountMl = current.amountMl + amount)
                repository.updateWater(updated)
                _waterAmount.value = updated.amountMl
            } else {
                val newIntake = WaterIntake(0, userId, today, amount)
                repository.addWater(newIntake)
                _waterAmount.value = amount
            }
        }
    }
}
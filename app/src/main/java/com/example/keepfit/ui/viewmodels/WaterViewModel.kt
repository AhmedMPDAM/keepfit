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
            val today = getTodayDate()
            val intake = repository.getTodayIntake(userId, today)
            _waterAmount.value = intake?.amountMl ?: 0
        }
    }

    fun addWater(userId: Int, amount: Int) {
        viewModelScope.launch {
            val today = getTodayDate()
            val existingIntake = repository.getTodayIntake(userId, today)

            if (existingIntake != null) {
                 updateExistingWater(existingIntake, amount)
            } else {
                 createNewWater(userId, today, amount)
            }
        }
    } 

    private suspend fun updateExistingWater(existing: WaterIntake, amount: Int) {
        val newAmount = existing.amountMl + amount
        val updated = existing.copy(amountMl = newAmount)
        repository.updateWater(updated)
        _waterAmount.value = newAmount
    } 
    private suspend fun createNewWater(userId: Int, date: String, amount: Int) {
        val newIntake = WaterIntake(0, userId, date, amount)
        repository.addWater(newIntake)
        _waterAmount.value = amount
    }
 
    private fun getTodayDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }
}
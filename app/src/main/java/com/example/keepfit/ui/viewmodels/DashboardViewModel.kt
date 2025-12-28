package com.example.keepfit.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.keepfit.data.local.entities.User
import com.example.keepfit.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(private val repository: UserRepository) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _randomTip = MutableStateFlow("")
    val randomTip: StateFlow<String> = _randomTip

    private val tips = listOf(
        "Drink water regularly",
        "Exercise 30 minutes daily",
        "Get 8 hours of sleep",
        "Eat balanced meals",
        "Stay consistent"
    )

    fun loadUser(userId: Int) {
        viewModelScope.launch {
            _user.value = repository.getUserById(userId)
            _randomTip.value = tips.random()
        }
    }

    fun updateMetrics(weight: Double, height: Double) {
        viewModelScope.launch {
            _user.value?.let {
                val updated = it.copy(weight = weight, height = height)
                repository.updateUser(updated)
                _user.value = updated
            }
        }
    }
}
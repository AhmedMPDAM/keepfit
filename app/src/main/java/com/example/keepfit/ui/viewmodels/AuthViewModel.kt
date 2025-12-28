package com.example.keepfit.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.keepfit.data.local.entities.User
import com.example.keepfit.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: UserRepository) : ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    fun register(fullName: String, email: String, password: String, age: Int,
                 height: Double, weight: Double, goal: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val hashedPassword = password.hashCode().toString()
            val user = User(0, fullName, email, hashedPassword, age, height, weight, goal)
            val userId = repository.register(user)
            _currentUser.value = user.copy(id = userId.toInt())
            onSuccess()
        }
    }

    fun login(email: String, password: String, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            val hashedPassword = password.hashCode().toString()
            val user = repository.login(email, hashedPassword)
            if (user != null) {
                _currentUser.value = user
                onSuccess()
            } else {
                onError()
            }
        }
    }
}
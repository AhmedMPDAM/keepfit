package com.example.keepfit.data.repository

import com.example.keepfit.data.local.UserDao
import com.example.keepfit.data.local.entities.User

class UserRepository(private val userDao: UserDao) {
    suspend fun register(user: User): Long = userDao.insert(user)
    suspend fun login(email: String, password: String): User? = userDao.login(email, password)
    suspend fun getUserById(userId: Int): User? = userDao.getUserById(userId)
    suspend fun updateUser(user: User) = userDao.update(user)
}
package com.example.keepfit.data.repository

import com.example.keepfit.data.local.WaterIntakeDao
import com.example.keepfit.data.local.entities.WaterIntake

class WaterRepository(private val waterDao: WaterIntakeDao) {
    suspend fun getTodayIntake(userId: Int, date: String): WaterIntake? =
        waterDao.getTodayIntake(userId, date)

    suspend fun addWater(waterIntake: WaterIntake) = waterDao.insert(waterIntake)
    suspend fun updateWater(waterIntake: WaterIntake) = waterDao.update(waterIntake)
}
package com.example.keepfit.data.local

import androidx.room.*
import com.example.keepfit.data.local.entities.WaterIntake

@Dao
interface WaterIntakeDao {
    @Insert
    suspend fun insert(waterIntake: WaterIntake)

    @Query("SELECT * FROM water_intake WHERE userId = :userId AND date = :date LIMIT 1")
    suspend fun getTodayIntake(userId: Int, date: String): WaterIntake?

    @Update
    suspend fun update(waterIntake: WaterIntake)
}
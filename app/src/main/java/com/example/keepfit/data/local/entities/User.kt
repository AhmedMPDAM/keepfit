package com.example.keepfit.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fullName: String,
    val email: String,
    val password: String,
    val age: Int,
    val height: Double,
    val weight: Double,
    val goal: String="LOSE_FAT" // LOSE_FAT, BUILD_MUSCLE, BOTH
)
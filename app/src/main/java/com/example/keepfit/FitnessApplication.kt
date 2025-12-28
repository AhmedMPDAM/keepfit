package com.example.keepfit

import android.app.Application
import com.example.keepfit.data.local.AppDatabase

class FitnessApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}
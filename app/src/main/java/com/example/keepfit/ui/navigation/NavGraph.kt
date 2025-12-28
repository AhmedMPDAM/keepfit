package com.example.keepfit.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.keepfit.FitnessApplication
import com.example.keepfit.data.repository.UserRepository
import com.example.keepfit.data.repository.WaterRepository
import com.example.keepfit.ui.screens.*
import com.example.keepfit.ui.viewmodels.*
import androidx.compose.ui.platform.LocalContext

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val app = context.applicationContext as FitnessApplication

    val userRepo = UserRepository(app.database.userDao())
    val waterRepo = WaterRepository(app.database.waterIntakeDao())

    val authViewModel: AuthViewModel = viewModel(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            return AuthViewModel(userRepo) as T
        }
    })

    val dashboardViewModel: DashboardViewModel = viewModel(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            return DashboardViewModel(userRepo) as T
        }
    })

    val waterViewModel: WaterViewModel = viewModel(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            return WaterViewModel(waterRepo) as T
        }
    })

    val weatherViewModel: WeatherViewModel = viewModel()
    val timerViewModel: TimerViewModel = viewModel()

    val currentUser by authViewModel.currentUser.collectAsState()
    val userId = currentUser?.id ?: 0

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                viewModel = authViewModel,
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToDashboard = { navController.navigate("dashboard") }
            )
        }

        composable("register") {
            RegisterScreen(
                viewModel = authViewModel,
                onNavigateToDashboard = { navController.navigate("dashboard") }
            )
        }

        composable("dashboard") {
            DashboardScreen(
                viewModel = dashboardViewModel,
                userId = userId,
                onNavigateToWater = { navController.navigate("water") },
                onNavigateToWeather = { navController.navigate("weather") },
                onNavigateToNutrition = { navController.navigate("nutrition") },
                onNavigateToBMI = { navController.navigate("bmi") },
                onNavigateToExercises = { navController.navigate("exercises") },
                onNavigateToTimer = { navController.navigate("timer") }
            )
        }

        composable("water") {
            WaterTrackerScreen(viewModel = waterViewModel, userId = userId)
        }

        composable("weather") {
            WeatherScreen(viewModel = weatherViewModel)
        }

        composable("nutrition") {
            NutritionScreen(weight = currentUser?.weight ?: 70.0)
        }

        composable("bmi") {
            BMIScreen(
                initialHeight = currentUser?.height ?: 170.0,
                initialWeight = currentUser?.weight ?: 70.0
            )
        }

        composable("exercises") {
            ExercisesScreen()
        }

        composable("timer") {
            TimerScreen(viewModel = timerViewModel)
        }
    }
}
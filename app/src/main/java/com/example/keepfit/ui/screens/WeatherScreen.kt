package com.example.keepfit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.keepfit.ui.theme.*
import com.example.keepfit.ui.viewmodels.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val temperature by viewModel.temperature.collectAsState()
    val suggestion by viewModel.suggestion.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchWeather()
    }

    val weatherIcon = when {
        temperature > 26 -> "🌡️"
        temperature < 16 -> "🌧️"
        else -> "☀️"
    }

    val gradientColors = when {
        temperature > 26 -> listOf(Color(0xFFFF6B6B), Color(0xFFEE5A6F))
        temperature < 16 -> listOf(Color(0xFF4A69BD), Color(0xFF6A89CC))
        else -> listOf(Color(0xFFFFA502), Color(0xFFFFB142))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(gradientColors)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                weatherIcon,
                fontSize = 120.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "${temperature.toInt()}°C",
                style = MaterialTheme.typography.displayLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 72.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.9f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Workout Suggestion",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        suggestion,
                        style = MaterialTheme.typography.headlineSmall,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { viewModel.fetchWeather() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = gradientColors[0]
                )
            ) {
                Text(
                    "Refresh Weather",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
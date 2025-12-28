package com.example.keepfit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.keepfit.ui.theme.*

@Composable
fun NutritionScreen(weight: Double) {
    val suggestions = when {
        weight > 75 -> listOf("Focus on cardio", "Reduce calorie intake", "Increase protein")
        weight >= 55 -> listOf("Mix cardio and strength", "Balanced diet", "Stay consistent")
        else -> listOf("Light workouts", "Increase calorie intake", "Build strength gradually")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
            Text(
                "Nutrition Suggestions",
                style = MaterialTheme.typography.headlineMedium,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            suggestions.forEachIndexed { index, suggestion ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            "${index + 1}",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = FitnessPrimary
                        )
                        Text(
                            suggestion,
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextPrimary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
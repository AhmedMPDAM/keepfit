package com.example.keepfit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.keepfit.ui.theme.*

@Composable
fun BMIScreen(initialHeight: Double, initialWeight: Double) {
    var height by remember { mutableStateOf(initialHeight.toString()) }
    var weight by remember { mutableStateOf(initialWeight.toString()) }

     val bmi = calculateBMI(height, weight)
    
     val categoryInfo = getBMICategory(bmi)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "BMI Calculator",
                style = MaterialTheme.typography.headlineMedium,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))
 
            BMIResultCard(bmi = bmi, categoryInfo = categoryInfo)

            Spacer(modifier = Modifier.height(32.dp))
 
            BMIInputCard(
                height = height,
                weight = weight,
                onHeightChange = { height = it },
                onWeightChange = { weight = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

             BMIScaleCard()
        }
    }
}
 
fun calculateBMI(heightStr: String, weightStr: String): Double {
    val height = heightStr.toDoubleOrNull() ?: 0.0
    val weight = weightStr.toDoubleOrNull() ?: 0.0
    
    if (height > 0) {
        val heightInMeters = height / 100
        return weight / (heightInMeters * heightInMeters)
    }
    return 0.0
}
 
fun getBMICategory(bmi: Double): BMICategoryInfo {
    return when {
        bmi < 18.5 -> BMICategoryInfo("Underweight", Color(0xFF3498DB), "⚠️")
        bmi < 25 -> BMICategoryInfo("Normal", SuccessGreen, "✅")
        bmi < 30 -> BMICategoryInfo("Overweight", HydrationYellow, "⚡")
        else -> BMICategoryInfo("Obese", HydrationRed, "🔴")
    }
}

data class BMICategoryInfo(
    val name: String,
    val color: Color,
    val emoji: String
)

@Composable
fun BMIResultCard(bmi: Double, categoryInfo: BMICategoryInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = categoryInfo.color.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(categoryInfo.emoji, fontSize = 64.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                String.format("%.1f", bmi),
                style = MaterialTheme.typography.displayLarge,
                color = categoryInfo.color,
                fontWeight = FontWeight.Bold
            )
            Text(
                categoryInfo.name,
                style = MaterialTheme.typography.titleLarge,
                color = categoryInfo.color,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun BMIInputCard(
    height: String,
    weight: String,
    onHeightChange: (String) -> Unit,
    onWeightChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                "Your Measurements",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = height,
                onValueChange = onHeightChange,
                label = { Text("Height (cm)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = FitnessPrimary,
                    unfocusedBorderColor = TextLight
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = weight,
                onValueChange = onWeightChange,
                label = { Text("Weight (kg)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = FitnessPrimary,
                    unfocusedBorderColor = TextLight
                )
            )
        }
    }
}

@Composable
fun BMIScaleCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                "BMI Scale",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            BMIScaleItem("< 18.5", "Underweight", Color(0xFF3498DB))
            BMIScaleItem("18.5 - 24.9", "Normal", SuccessGreen)
            BMIScaleItem("25 - 29.9", "Overweight", HydrationYellow)
            BMIScaleItem("≥ 30", "Obese", HydrationRed)
        }
    }
}

@Composable
fun BMIScaleItem(range: String, label: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(color, RoundedCornerShape(6.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                label,
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary
            )
        }
        Text(
            range,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            fontWeight = FontWeight.Medium
        )
    }
}
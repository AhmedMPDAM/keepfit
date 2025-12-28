package com.example.keepfit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.keepfit.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExercisesScreen() {
    var expanded by remember { mutableStateOf(false) }
    var selectedGoal by remember { mutableStateOf("LOSE_FAT") }
    val goals = listOf("LOSE_FAT", "BUILD_MUSCLE", "BOTH")

    val exercises = when (selectedGoal) {
        "LOSE_FAT" -> listOf("Running", "Cycling", "Swimming", "Jump rope")
        "BUILD_MUSCLE" -> listOf("Bench press", "Squats", "Deadlifts", "Pull-ups")
        else -> listOf("Running", "Squats", "Push-ups", "Planks", "Cycling")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
            Text(
                "Exercises",
                style = MaterialTheme.typography.headlineMedium,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedGoal.replace("_", " "),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Goal") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = FitnessPrimary,
                        unfocusedBorderColor = TextLight
                    )
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    goals.forEach { goal ->
                        DropdownMenuItem(
                            text = { Text(goal.replace("_", " ")) },
                            onClick = {
                                selectedGoal = goal
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            exercises.forEachIndexed { index, exercise ->
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
                            "💪",
                            fontSize = 28.sp
                        )
                        Text(
                            exercise,
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
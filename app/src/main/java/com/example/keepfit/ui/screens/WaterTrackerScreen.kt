package com.example.keepfit.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.keepfit.ui.theme.*
import com.example.keepfit.ui.viewmodels.WaterViewModel

@Composable
fun WaterTrackerScreen(viewModel: WaterViewModel, userId: Int) {
    val waterAmount by viewModel.waterAmount.collectAsState()
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    LaunchedEffect(userId) {
        viewModel.loadTodayWater(userId)
    }

    val liters = waterAmount / 1000.0
    val percentage = (liters / 3.0 * 100).coerceIn(0.0, 100.0)

    val (color, gradientColors) = when {
        liters < 1.0 -> HydrationRed to listOf(Color(0xFFFF6B6B), Color(0xFFEE5A6F))
        liters < 2.0 -> HydrationYellow to listOf(Color(0xFFFFA502), Color(0xFFFFB142))
        else -> HydrationGreen to listOf(Color(0xFF26DE81), Color(0xFF20BF6B))
    }

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
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Water Tracker",
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )

                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.2f))
                ) {
                    Text(
                        "${percentage.toInt()}%",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        color = color,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Water Glass Animation
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .scale(scale)
                    .clip(CircleShape)
                    .background(
                        Brush.verticalGradient(gradientColors)
                    )
                    .clickable {
                        isPressed = true
                        viewModel.addWater(userId, 250)
                    }
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "💧",
                    fontSize = 80.sp
                )
            }

            LaunchedEffect(isPressed) {
                if (isPressed) {
                    kotlinx.coroutines.delay(200)
                    isPressed = false
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Water Amount Display
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Today's Intake",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "${String.format("%.2f", liters)} L",
                        style = MaterialTheme.typography.displayLarge,
                        color = color,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LinearProgressIndicator(
                        progress = (percentage / 100).toFloat(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp)
                            .clip(RoundedCornerShape(6.dp)),
                        color = color,
                        trackColor = color.copy(alpha = 0.2f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Goal: 3.0 L",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = FitnessPrimary.copy(alpha = 0.1f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("ℹ️", fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "Tap the water drop to add 250ml. Stay hydrated for better health and performance!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary
                    )
                }
            }
        }
    }
}
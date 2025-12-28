package com.example.keepfit.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = FitnessPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE8E6FF),
    secondary = FitnessSecondary,
    onSecondary = Color.White,
    tertiary = FitnessAccent,
    background = BackgroundLight,
    surface = SurfaceLight,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    error = ErrorRed,
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = FitnessPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF4A42CC),
    secondary = FitnessSecondary,
    onSecondary = Color.Black,
    tertiary = FitnessAccent,
    background = BackgroundDark,
    surface = SurfaceDark,
    onBackground = Color.White,
    onSurface = Color.White,
    error = ErrorRed,
    onError = Color.White
)

@Composable
fun KeepfitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
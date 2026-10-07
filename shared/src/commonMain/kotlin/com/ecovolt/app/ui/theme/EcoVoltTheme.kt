package com.ecovolt.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Paleta de EcoVolt: verdes de ahorro de energía con un amarillo de "volt"
private val ColoresEcoVolt = lightColorScheme(
    primary = Color(0xFF1B7F3B),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB7E4C7),
    onPrimaryContainer = Color(0xFF0B3D1E),
    secondary = Color(0xFFF2B705),
    onSecondary = Color(0xFF3A2E00),
    background = Color(0xFFF3FAF5),
    onBackground = Color(0xFF14231A),
    surface = Color.White,
    onSurface = Color(0xFF14231A),
    surfaceVariant = Color(0xFFE3F1E8),
    onSurfaceVariant = Color(0xFF445A4D),
    error = Color(0xFFB3261E),
    errorContainer = Color(0xFFFCE4E2),
    onErrorContainer = Color(0xFF5C1511)
)

@Composable
fun EcoVoltTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = ColoresEcoVolt, content = content)
}

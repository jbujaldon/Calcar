package com.calcar.common.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CalcarLightColorScheme = lightColorScheme(
    primary = Primary40,
    secondary = Secondary40,
    onSecondary = Color.White,
    secondaryContainer = Secondary90,
    onSecondaryContainer = Secondary10,
    onSurface = Neutral10,
    onSurfaceVariant = NeutralVariant30,
    outlineVariant = NeutralVariant50,
    surfaceContainerLow = Neutral96,
    surfaceContainerHighest = Neutral90,
)

@Composable
fun CalcarTheme(
    // isInDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    // val context = LocalContext.current
    // val isDynamicColorEnabled = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    /*
    val colors = when {
        isDynamicColorEnabled -> dynamicLightColorScheme(context)
        else -> CalcarLightColorScheme
    }
    */
    MaterialTheme(
        colorScheme = CalcarLightColorScheme,
        typography = CalcarTypography,
        content = content,
    )
}
/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val lightScheme =
    lightColorScheme(
        primary = primaryLight,
        onPrimary = onPrimaryLight,
        primaryContainer = primaryContainerLight,
        onPrimaryContainer = onPrimaryContainerLight,
        secondary = secondaryLight,
        onSecondary = onSecondaryLight,
        secondaryContainer = secondaryContainerLight,
        onSecondaryContainer = onSecondaryContainerLight,
        tertiary = tertiaryLight,
        onTertiary = onTertiaryLight,
        tertiaryContainer = tertiaryContainerLight,
        onTertiaryContainer = onTertiaryContainerLight,
        error = errorLight,
        onError = onErrorLight,
        errorContainer = errorContainerLight,
        onErrorContainer = onErrorContainerLight,
        background = backgroundLight,
        onBackground = onBackgroundLight,
        surface = surfaceLight,
        onSurface = onSurfaceLight,
        surfaceVariant = surfaceVariantLight,
        onSurfaceVariant = onSurfaceVariantLight,
        outline = outlineLight,
        outlineVariant = outlineVariantLight,
        scrim = scrimLight,
        inverseSurface = inverseSurfaceLight,
        inverseOnSurface = inverseOnSurfaceLight,
        inversePrimary = inversePrimaryLight,
        surfaceDim = surfaceDimLight,
        surfaceBright = surfaceBrightLight,
        surfaceContainerLowest = surfaceContainerLowestLight,
        surfaceContainerLow = surfaceContainerLowLight,
        surfaceContainer = surfaceContainerLight,
        surfaceContainerHigh = surfaceContainerHighLight,
        surfaceContainerHighest = surfaceContainerHighestLight,
    )

val darkScheme =
    darkColorScheme(
        primary = primaryDark,
        onPrimary = onPrimaryDark,
        primaryContainer = primaryContainerDark,
        onPrimaryContainer = onPrimaryContainerDark,
        secondary = secondaryDark,
        onSecondary = onSecondaryDark,
        secondaryContainer = secondaryContainerDark,
        onSecondaryContainer = onSecondaryContainerDark,
        tertiary = tertiaryDark,
        onTertiary = onTertiaryDark,
        tertiaryContainer = tertiaryContainerDark,
        onTertiaryContainer = onTertiaryContainerDark,
        error = errorDark,
        onError = onErrorDark,
        errorContainer = errorContainerDark,
        onErrorContainer = onErrorContainerDark,
        background = backgroundDark,
        onBackground = onBackgroundDark,
        surface = surfaceDark,
        onSurface = onSurfaceDark,
        surfaceVariant = surfaceVariantDark,
        onSurfaceVariant = onSurfaceVariantDark,
        outline = outlineDark,
        outlineVariant = outlineVariantDark,
        scrim = scrimDark,
        inverseSurface = inverseSurfaceDark,
        inverseOnSurface = inverseOnSurfaceDark,
        inversePrimary = inversePrimaryDark,
        surfaceDim = surfaceDimDark,
        surfaceBright = surfaceBrightDark,
        surfaceContainerLowest = surfaceContainerLowestDark,
        surfaceContainerLow = surfaceContainerLowDark,
        surfaceContainer = surfaceContainerDark,
        surfaceContainerHigh = surfaceContainerHighDark,
        surfaceContainerHighest = surfaceContainerHighestDark,
    )

/**
 * Light Android theme color scheme
 */
val LightAndroidColorScheme =
    lightColorScheme(
        primary = Green40,
        onPrimary = Color.White,
        primaryContainer = Green90,
        onPrimaryContainer = Green10,
        secondary = DarkGreen40,
        onSecondary = Color.White,
        secondaryContainer = DarkGreen90,
        onSecondaryContainer = DarkGreen10,
        tertiary = Teal40,
        onTertiary = Color.White,
        tertiaryContainer = Teal90,
        onTertiaryContainer = Teal10,
        error = Red40,
        onError = Color.White,
        errorContainer = Red90,
        onErrorContainer = Red10,
        background = DarkGreenGray99,
        onBackground = DarkGreenGray10,
        surface = DarkGreenGray99,
        onSurface = DarkGreenGray10,
        surfaceVariant = GreenGray90,
        onSurfaceVariant = GreenGray30,
        inverseSurface = DarkGreenGray20,
        inverseOnSurface = DarkGreenGray95,
        outline = GreenGray50,
    )

/**
 * Dark Android theme color scheme
 */
val DarkAndroidColorScheme =
    darkColorScheme(
        primary = Green80,
        onPrimary = Green20,
        primaryContainer = Green30,
        onPrimaryContainer = Green90,
        secondary = DarkGreen80,
        onSecondary = DarkGreen20,
        secondaryContainer = DarkGreen30,
        onSecondaryContainer = DarkGreen90,
        tertiary = Teal80,
        onTertiary = Teal20,
        tertiaryContainer = Teal30,
        onTertiaryContainer = Teal90,
        error = Red80,
        onError = Red20,
        errorContainer = Red30,
        onErrorContainer = Red90,
        background = DarkGreenGray10,
        onBackground = DarkGreenGray90,
        surface = DarkGreenGray10,
        onSurface = DarkGreenGray90,
        surfaceVariant = GreenGray30,
        onSurfaceVariant = GreenGray80,
        inverseSurface = DarkGreenGray90,
        inverseOnSurface = DarkGreenGray10,
        outline = GreenGray60,
    )

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    androidTheme: Boolean = false,
    // Dynamic color is available on Android 12+
    shouldDisplayDynamicTheming: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            androidTheme -> if (darkTheme) DarkAndroidColorScheme else LightAndroidColorScheme
            else -> colorScheme(darkTheme, shouldDisplayDynamicTheming)
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = appTypography(),
        content = content,
    )
}

@Composable
expect fun colorScheme(
    useDarkTheme: Boolean,
    dynamicColor: Boolean,
): ColorScheme

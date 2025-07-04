/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import neurochat.core.designsystem.generated.resources.Res
import neurochat.core.designsystem.generated.resources.outfit_black
import neurochat.core.designsystem.generated.resources.outfit_bold
import neurochat.core.designsystem.generated.resources.outfit_extra_bold
import neurochat.core.designsystem.generated.resources.outfit_extra_light
import neurochat.core.designsystem.generated.resources.outfit_light
import neurochat.core.designsystem.generated.resources.outfit_medium
import neurochat.core.designsystem.generated.resources.outfit_regular
import neurochat.core.designsystem.generated.resources.outfit_semi_bold
import neurochat.core.designsystem.generated.resources.outfit_thin
import org.jetbrains.compose.resources.Font

@Composable
private fun fontFamily(): FontFamily = FontFamily(
    Font(Res.font.outfit_black, FontWeight.Black),
    Font(Res.font.outfit_bold, FontWeight.Bold),
    Font(Res.font.outfit_semi_bold, FontWeight.SemiBold),
    Font(Res.font.outfit_medium, FontWeight.Medium),
    Font(Res.font.outfit_regular, FontWeight.Normal),
    Font(Res.font.outfit_light, FontWeight.Light),
    Font(Res.font.outfit_thin, FontWeight.Thin),
    Font(Res.font.outfit_extra_light, FontWeight.ExtraLight),
    Font(Res.font.outfit_extra_bold, FontWeight.ExtraBold),
)

// Set of Material typography styles to start with
@Composable
internal fun appTypography() = Typography(
    displayLarge =
    TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
    ),
    displayMedium =
    TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
    ),
    displaySmall =
    TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
    ),
    headlineLarge =
    TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
    ),
    headlineMedium =
    TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
    ),
    headlineSmall =
    TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        lineHeightStyle =
        LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Bottom,
            trim = LineHeightStyle.Trim.None,
        ),
    ),
    titleLarge =
    TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 30.24.sp,
    ),
    titleMedium =
    TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.1.sp,
    ),
    titleSmall =
    TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    // Default text style
    bodyLarge =
    TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        lineHeightStyle =
        LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None,
        ),
    ),
    bodyMedium =
    TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    ),
    bodySmall =
    TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
    // Used for Button
    labelLarge =
    TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    // Used for Navigation items
    labelMedium =
    TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        lineHeightStyle =
        LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.LastLineBottom,
        ),
    ),
    // Used for Tag
    labelSmall =
    TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.sp,
        lineHeightStyle =
        LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.LastLineBottom,
        ),
    ),
)

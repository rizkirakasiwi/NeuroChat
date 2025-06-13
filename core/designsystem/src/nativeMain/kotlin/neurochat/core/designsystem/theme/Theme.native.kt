/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

@Composable
actual fun colorScheme(
    useDarkTheme: Boolean,
    dynamicColor: Boolean,
): ColorScheme = when {
    useDarkTheme -> darkScheme
    else -> lightScheme
}

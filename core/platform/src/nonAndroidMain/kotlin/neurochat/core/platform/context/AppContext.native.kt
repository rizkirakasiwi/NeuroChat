/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.platform.context

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

actual abstract class AppContext private constructor() {
    companion object {
        val INSTANCE = object : AppContext() {}
    }
}

actual val LocalContext: ProvidableCompositionLocal<AppContext>
    get() = staticCompositionLocalOf { AppContext.INSTANCE }

actual val AppContext.activity: Any
    @Composable
    get() = AppContext.INSTANCE

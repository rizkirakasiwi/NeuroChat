/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.platform.context

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal

/**
 * Android-specific implementation of AppContext.
 *
 * For Android platforms, the application context is represented by Android's native
 * Context class, which provides access to application-specific resources, system services,
 * and other platform functionality.
 */
actual typealias AppContext = android.content.Context

/**
 * Android-specific implementation of LocalContext.
 *
 * This delegates to the standard LocalContext provided by the Jetpack Compose UI library,
 * which makes the current Android Context available throughout the composition hierarchy.
 *
 * @return A CompositionLocal containing the current Android Context
 */
actual val LocalContext: ProvidableCompositionLocal<AppContext>
    get() = androidx.compose.ui.platform.LocalContext

/**
 * Android-specific implementation of the activity property.
 *
 * Retrieves the current Activity instance from the LocalActivity CompositionLocal.
 * This implementation requires that a valid Activity is present in the composition hierarchy;
 * otherwise, it will throw an IllegalStateException.
 *
 * @throws IllegalStateException if no Activity is available in the current composition
 * @return The current Activity instance
 */
actual val AppContext.activity: Any
    @Composable
    get() = requireNotNull(LocalActivity.current)

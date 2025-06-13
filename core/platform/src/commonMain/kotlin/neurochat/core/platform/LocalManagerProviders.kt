/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import neurochat.core.platform.context.AppContext
import neurochat.core.platform.intent.IntentManager

/**
 * A composable function that provides platform-specific managers to the composition tree.
 *
 * This function initializes and provides various platform-specific managers
 * (AppReviewManager, IntentManager, AppUpdateManager) to the composition through
 * CompositionLocal providers. It acts as a central point for injecting
 * platform-specific functionality into the Compose UI hierarchy.
 *
 * As an expect function, platform-specific implementations will be provided in
 * each target platform's source set, allowing for platform-specific initialization
 * while maintaining a consistent API across platforms.
 *
 * @param context The platform-specific AppContext to initialize the managers
 * @param content The composable content where the managers will be available
 */
@Composable
expect fun LocalManagerProvider(
    context: AppContext,
    content: @Composable () -> Unit,
)

/**
 * Provides access to the intent manager throughout the app.
 */
val LocalIntentManager: ProvidableCompositionLocal<IntentManager> =
    compositionLocalOf {
        error("CompositionLocal LocalIntentManager not present")
    }

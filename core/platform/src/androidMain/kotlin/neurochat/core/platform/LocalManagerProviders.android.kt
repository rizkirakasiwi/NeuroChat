/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.platform

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import neurochat.core.platform.context.AppContext
import neurochat.core.platform.context.activity
import neurochat.core.platform.intent.IntentManagerImpl

/**
 * Android-specific implementation of the LocalManagerProvider composable function.
 *
 * This implementation initializes the platform-specific manager instances required
 * by the application and provides them to the composition hierarchy through
 * CompositionLocal providers. The function handles the creation and provision of:
 *
 * - AppReviewManager: For managing in-app review requests through Google Play
 * - IntentManager: For handling Android-specific intent operations
 * - AppUpdateManager: For managing application updates through Google Play
 *
 * The function retrieves the current Activity from the provided AppContext and uses
 * it to initialize each manager implementation. All managers are then made available
 * to child composables through their respective CompositionLocal providers.
 *
 * @param context The Android Context used to initialize the managers
 * @param content The composable content where the managers will be available
 */
@Composable
actual fun LocalManagerProvider(
    context: AppContext,
    content: @Composable () -> Unit,
) {
    val activity = context.activity as Activity
    CompositionLocalProvider(
        LocalIntentManager provides IntentManagerImpl(activity),
    ) {
        content()
    }
}

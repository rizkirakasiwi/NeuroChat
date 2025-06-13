/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import neurochat.core.platform.context.AppContext
import neurochat.core.platform.intent.IntentManagerImpl

@Composable
actual fun LocalManagerProvider(
    context: AppContext,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalIntentManager provides IntentManagerImpl(),
    ) {
        content()
    }
}

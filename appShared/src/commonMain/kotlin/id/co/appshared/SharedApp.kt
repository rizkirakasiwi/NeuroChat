/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package id.co.appshared

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.LocalPlatformContext
import id.co.appnavigation.ComposeApp
import neurochat.core.platform.LocalManagerProvider
import neurochat.core.platform.context.LocalContext
import neurochat.core.ui.LocalImageLoaderProvider
import neurochat.core.ui.rememberImageLoader

@Composable
fun SharedApp(modifier: Modifier = Modifier) {
    LocalManagerProvider(LocalContext.current) {
        LocalImageLoaderProvider(rememberImageLoader(LocalPlatformContext.current)) {
            ComposeApp(modifier = modifier)
        }
    }
}

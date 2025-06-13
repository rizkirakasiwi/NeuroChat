/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package id.co.appnavigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import id.co.appnavigation.navigation.RootNavGraph
import neurochat.core.data.utils.NetworkMonitor
import neurochat.core.data.utils.TimeZoneMonitor
import neurochat.core.designsystem.theme.AppTheme
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ComposeApp(
    modifier: Modifier = Modifier,
    networkMonitor: NetworkMonitor = koinInject(),
    timeZoneMonitor: TimeZoneMonitor = koinInject(),
) {
    val viewModel: AppViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val isSystemInDarkTheme = isSystemInDarkTheme()

    AppTheme(
        darkTheme = uiState.shouldUseDarkTheme(isSystemInDarkTheme),
        androidTheme = uiState.shouldUseAndroidTheme,
        shouldDisplayDynamicTheming = uiState.shouldDisplayDynamicTheming,
    ) {
        RootNavGraph(
            networkMonitor = networkMonitor,
            timeZoneMonitor = timeZoneMonitor,
            navHostController = rememberNavController(),
            modifier = modifier,
        )
    }
}

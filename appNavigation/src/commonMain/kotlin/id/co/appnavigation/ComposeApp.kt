/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package id.co.appnavigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import id.co.appnavigation.navigation.RootNavGraph
import neurochat.core.designsystem.theme.AppTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ComposeApp(
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
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
            navHostController = rememberNavController(),
            modifier = modifier,
            windowSizeClass = windowSizeClass,
        )
    }
}

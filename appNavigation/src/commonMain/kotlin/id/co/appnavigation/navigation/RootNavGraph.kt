/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package id.co.appnavigation.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import id.co.appnavigation.navigation.NavGraphRoute.MAIN_GRAPH
import id.co.appnavigation.ui.App
import id.co.appnavigation.ui.MainViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RootNavGraph(
    windowSizeClass: WindowSizeClass,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navHostController,
        startDestination = MAIN_GRAPH,
        route = NavGraphRoute.ROOT_GRAPH,
        modifier = modifier,
    ) {
        composable(MAIN_GRAPH) {
            val viewModel: MainViewModel = koinViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val isOffline by viewModel.isOffline.collectAsStateWithLifecycle(initialValue = false)
            val isCompact = windowSizeClass.widthSizeClass < WindowWidthSizeClass.Medium

            LaunchedEffect(Unit) {
                viewModel.loadChatHistories()
            }

            App(
                state = state,
                isOffline = isOffline,
                isCompact = isCompact,
            )
        }
    }
}

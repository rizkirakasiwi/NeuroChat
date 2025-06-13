/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package id.co.appnavigation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import id.co.appnavigation.navigation.NavGraphRoute.MAIN_GRAPH
import id.co.appnavigation.ui.App
import neurochat.core.data.utils.NetworkMonitor
import neurochat.core.data.utils.TimeZoneMonitor

@Composable
fun RootNavGraph(
    networkMonitor: NetworkMonitor,
    timeZoneMonitor: TimeZoneMonitor,
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
            App(
                networkMonitor = networkMonitor,
                timeZoneMonitor = timeZoneMonitor,
            )
        }
    }
}

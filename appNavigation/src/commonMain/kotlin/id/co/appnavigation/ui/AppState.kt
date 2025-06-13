/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package id.co.appnavigation.ui

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import id.co.appnavigation.utils.TopLevelDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.TimeZone
import neurochat.core.data.utils.NetworkMonitor
import neurochat.core.data.utils.TimeZoneMonitor

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
internal fun rememberAppState(
    networkMonitor: NetworkMonitor,
    timeZoneMonitor: TimeZoneMonitor,
    windowSizeClass: WindowSizeClass = calculateWindowSizeClass(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): AppState = remember(
    navController,
    coroutineScope,
    windowSizeClass,
    networkMonitor,
    timeZoneMonitor,
) {
    AppState(
        navController = navController,
        coroutineScope = coroutineScope,
        windowSizeClass = windowSizeClass,
        networkMonitor = networkMonitor,
        timeZoneMonitor = timeZoneMonitor,
    )
}

@Stable
internal class AppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    timeZoneMonitor: TimeZoneMonitor,
) {
    val currentDestination: NavDestination?
        @Composable get() =
            navController
                .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() =
            when (currentDestination?.route) {
                "HOME_ROUTE" -> TopLevelDestination.HOME
                // must be constant.PROFILE_ROUTE
                "PROFILE_ROUTE" -> TopLevelDestination.PROFILE
                else -> null
            }

    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val shouldShowNavRail: Boolean
        get() = !shouldShowBottomBar

    val isOffline =
        networkMonitor.isOnline
            .map(Boolean::not)
            .stateIn(
                scope = coroutineScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false,
            )

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    val currentTimeZone =
        timeZoneMonitor.currentTimeZone
            .stateIn(
                coroutineScope,
                SharingStarted.WhileSubscribed(5_000),
                TimeZone.currentSystemDefault(),
            )

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions =
                navOptions {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }

            when (topLevelDestination) {
                TopLevelDestination.HOME -> {
                    // navController.navigateToHome(topLevelNavOptions)
                }
                TopLevelDestination.PROFILE -> {
                    // navController.navigateToProfile(topLevelNavOptions)
                }
            }
        }
    }
}

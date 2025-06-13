/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package id.co.appnavigation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import app.navigation.generated.resources.Res
import app.navigation.generated.resources.not_connected
import id.co.appnavigation.navigation.FeatureNavHost
import id.co.appnavigation.utils.TopLevelDestination
import neurochat.core.data.utils.NetworkMonitor
import neurochat.core.data.utils.TimeZoneMonitor
import neurochat.core.designsystem.component.AppNavigationBar
import neurochat.core.designsystem.component.AppNavigationBarItem
import neurochat.core.designsystem.component.AppNavigationRail
import neurochat.core.designsystem.component.AppNavigationRailItem
import neurochat.core.designsystem.icon.AppIcons
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun App(
    networkMonitor: NetworkMonitor,
    timeZoneMonitor: TimeZoneMonitor,
    modifier: Modifier = Modifier,
) {
    val appState =
        rememberAppState(
            networkMonitor = networkMonitor,
            timeZoneMonitor = timeZoneMonitor,
        )

    val snackbarHostState = remember { SnackbarHostState() }
    val destination = appState.currentTopLevelDestination

    val isOffline by appState.isOffline.collectAsStateWithLifecycle()

    // If user is not connected to the internet show a snack bar to inform them.
    val notConnectedMessage = stringResource(Res.string.not_connected)
    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackbarHostState.showSnackbar(
                message = notConnectedMessage,
                duration = Indefinite,
            )
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (appState.shouldShowBottomBar && destination != null) {
                BottomBar(
                    destinations = appState.topLevelDestinations,
                    destinationsWithUnreadResources = emptySet(),
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.currentDestination,
                    modifier = Modifier.testTag("NiaBottomBar"),
                )
            }
        },
    ) { padding ->
        Row(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
        ) {
            if (appState.shouldShowNavRail && destination != null) {
                NavRail(
                    destinations = appState.topLevelDestinations,
                    destinationsWithUnreadResources = emptySet(),
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.currentDestination,
                    modifier =
                        Modifier
                            .testTag("CmpNavRail")
                            .safeDrawingPadding(),
                )
            }

            Column(Modifier.fillMaxSize()) {
                // Show the top app bar on top level destinations.
                if (destination != null) {
                    AppBar(
                        title = stringResource(destination.titleText),
                        onNavigateToSettings = {
                            // appState.navController.navigateToSettings()
                        },
                        onNavigateToEditProfile = {},
                        onNavigateToNotification = {
                            // appState.navController.navigateToNotification()
                        },
                        destination = destination,
                    )
                }

                FeatureNavHost(
                    navController = appState.navController,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    title: String,
    onNavigateToSettings: () -> Unit,
    onNavigateToEditProfile: () -> Unit,
    onNavigateToNotification: () -> Unit,
    destination: TopLevelDestination?,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(text = title) },
        actions = {
            Box {
                when (destination) {
                    TopLevelDestination.HOME -> {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            IconButton(
                                onClick = onNavigateToNotification,
                            ) {
                                Icon(
                                    imageVector = AppIcons.OutlinedNotifications,
                                    contentDescription = "view notification",
                                )
                            }

                            IconButton(
                                onClick = onNavigateToSettings,
                            ) {
                                Icon(
                                    imageVector = AppIcons.SettingsOutlined,
                                    contentDescription = "view settings",
                                )
                            }
                        }
                    }

                    TopLevelDestination.PROFILE -> {
                        IconButton(
                            onClick = onNavigateToEditProfile,
                        ) {
                            Icon(
                                imageVector = AppIcons.Edit2,
                                contentDescription = "Edit Profile",
                            )
                        }
                    }

                    else -> {}
                }
            }
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
            ),
        modifier = modifier.testTag("ilmiTopAppBar"),
    )
}

@Composable
private fun NavRail(
    destinations: List<TopLevelDestination>,
    destinationsWithUnreadResources: Set<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    AppNavigationRail(modifier = modifier) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            val hasUnread = destinationsWithUnreadResources.contains(destination)
            AppNavigationRailItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                modifier = if (hasUnread) Modifier.notificationDot(
                    MaterialTheme.colorScheme.tertiary
                ) else Modifier,
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(destination.iconText)) },
            )
        }
    }
}

@Composable
private fun BottomBar(
    destinations: List<TopLevelDestination>,
    destinationsWithUnreadResources: Set<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    AppNavigationBar(
        modifier = modifier,
    ) {
        destinations.forEach { destination ->
            val hasUnread = destinationsWithUnreadResources.contains(destination)
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            AppNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                modifier = if (hasUnread) Modifier.notificationDot(
                    MaterialTheme.colorScheme.tertiary
                ) else Modifier,
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(destination.iconText)) },
            )
        }
    }
}

@Suppress("MagicNumber")
private fun Modifier.notificationDot(
    color: Color
): Modifier{
    return this then drawWithContent {
        drawContent()
        drawCircle(
            color,
            radius = 5.dp.toPx(),
            // This is based on the dimensions of the NavigationBar's "indicator pill";
            // however, its parameters are private, so we must depend on them implicitly
            // (NavigationBarTokens.ActiveIndicatorWidth = 64.dp)
            center =
                center +
                        Offset(
                            64.dp.toPx() * .45f,
                            32.dp.toPx() * -.45f - 6.dp.toPx(),
                        ),
        )
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) == true
    } == true

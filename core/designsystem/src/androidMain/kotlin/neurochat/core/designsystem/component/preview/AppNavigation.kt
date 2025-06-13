/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.designsystem.component.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Help
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import neurochat.core.designsystem.component.AppDrawerItem
import neurochat.core.designsystem.component.AppDrawerSheet
import neurochat.core.designsystem.component.AppNavigationBar
import neurochat.core.designsystem.component.AppNavigationBarItem
import neurochat.core.designsystem.component.AppNavigationDrawer
import neurochat.core.designsystem.component.AppNavigationRail
import neurochat.core.designsystem.component.AppNavigationRailItem
import neurochat.core.designsystem.component.AppTopAppBar
import neurochat.core.designsystem.component.NavigationIcon
import neurochat.core.designsystem.icon.AppIcons
import neurochat.core.designsystem.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun AppNavigationBarPreview() {
    val items = listOf("Home", "Payments", "Finance", "Profile")
    val icons =
        listOf(
            AppIcons.Home,
            AppIcons.Payment,
            AppIcons.Finance,
            AppIcons.Profile,
        )
    val selectedIcons =
        listOf(
            AppIcons.HomeBoarder,
            AppIcons.Payment,
            AppIcons.Finance,
            AppIcons.ProfileBoarder,
        )

    AppTheme {
        Scaffold {
            AppNavigationBar(modifier = Modifier.padding(it)) {
                items.forEachIndexed { index, item ->
                    AppNavigationBarItem(
                        selected = index == 0,
                        onClick = { },
                        icon = {
                            Icon(
                                imageVector = icons[index],
                                contentDescription = item,
                            )
                        },
                        selectedIcon = {
                            Icon(
                                imageVector = selectedIcons[index],
                                contentDescription = item,
                            )
                        },
                        label = { Text(item) },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun AppNavigationRailPreview() {
    val items = listOf("Home", "Payments", "Finance", "Profile")
    val icons =
        listOf(
            AppIcons.Home,
            AppIcons.Payment,
            AppIcons.Finance,
            AppIcons.Profile,
        )
    val selectedIcons =
        listOf(
            AppIcons.HomeBoarder,
            AppIcons.Payment,
            AppIcons.Finance,
            AppIcons.ProfileBoarder,
        )

    AppTheme {
        Scaffold {
            AppNavigationRail(modifier = Modifier.padding(it)) {
                items.forEachIndexed { index, item ->
                    AppNavigationRailItem(
                        selected = index == 0,
                        onClick = { },
                        icon = {
                            Icon(
                                imageVector = icons[index],
                                contentDescription = item,
                            )
                        },
                        selectedIcon = {
                            Icon(
                                imageVector = selectedIcons[index],
                                contentDescription = item,
                            )
                        },
                        label = { Text(item) },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun AppNavigationDrawPreview() {
    val state = rememberDrawerState(DrawerValue.Open)
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AppTopAppBar(
                title = "Navigation Drawer",
                navigationIcon = NavigationIcon(
                    navigationIcon = AppIcons.Menu,
                    navigationIconContentDescription = "Menu",
                    onNavigationIconClick = {
                        if (state.isClosed) {
                            scope.launch { state.open() }
                        } else {
                            scope.launch { state.close() }
                        }
                    },
                ),
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
            )
        },
    ) {
        Column(modifier = Modifier.padding(it)) {
            AppNavigationDrawer(
                state = state,
                drawerContent = {
                    AppDrawerSheet {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .verticalScroll(rememberScrollState()),
                        ) {
                            Spacer(Modifier.height(12.dp))
                            Text(
                                "Drawer Title",
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.titleLarge,
                            )
                            HorizontalDivider()

                            Text(
                                "Section 1",
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.titleMedium,
                            )
                            AppDrawerItem(
                                label = { Text("Item 1") },
                                selected = false,
                                onClick = { },
                            )
                            AppDrawerItem(
                                label = { Text("Item 2") },
                                selected = false,
                                onClick = { },
                            )

                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                            Text(
                                "Section 2",
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.titleMedium,
                            )
                            AppDrawerItem(
                                label = { Text("Settings") },
                                selected = false,
                                icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                                badge = { Text("20") },
                                onClick = { },
                            )
                            AppDrawerItem(
                                label = { Text("Help and feedback") },
                                selected = false,
                                icon = {
                                    Icon(
                                        Icons.AutoMirrored.Outlined.Help,
                                        contentDescription = null,
                                    )
                                },
                                onClick = { },
                            )
                            Spacer(Modifier.height(12.dp))
                        }
                    }
                },
                content = {
                },
            )
        }
    }
}

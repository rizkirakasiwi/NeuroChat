/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import neurochat.core.designsystem.icon.AppIcons
import neurochat.core.designsystem.theme.AppTheme
import neurochat.core.designsystem.utils.mirrorIfRtl
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String,
    onNavigationIconClick: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = { },
) {
    AppTopAppBar(
        title = title,
        scrollBehavior = scrollBehavior,
        navigationIcon =
        NavigationIcon(
            navigationIcon = navigationIcon,
            navigationIconContentDescription = navigationIconContentDescription,
            onNavigationIconClick = onNavigationIconClick,
        ),
        modifier = modifier,
        actions = actions,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("LongMethod")
@Composable
fun AppTopAppBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    navigationIcon: NavigationIcon?,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
) {
    var titleTextHasOverflow by remember {
        mutableStateOf(false)
    }

    val navigationIconContent: @Composable () -> Unit =
        remember(navigationIcon) {
            {
                navigationIcon?.let {
                    IconButton(
                        onClick = it.onNavigationIconClick,
                        modifier = Modifier.testTag("CloseButton"),
                    ) {
                        Icon(
                            modifier = Modifier.mirrorIfRtl(),
                            imageVector = it.navigationIcon,
                            contentDescription = it.navigationIconContentDescription,
                        )
                    }
                }
            }
        }

    val topAppBarColors =
        TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        )

    if (titleTextHasOverflow) {
        MediumTopAppBar(
            colors = topAppBarColors,
            scrollBehavior = scrollBehavior,
            navigationIcon = navigationIconContent,
            title = {
                // The height of the component is controlled and will only allow for 1 extra row,
                // making adding any arguments for softWrap and minLines superfluous.
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.testTag("PageTitleLabel"),
                )
            },
            modifier = modifier.testTag("HeaderBarComponent"),
            actions = actions,
        )
    } else {
        TopAppBar(
            colors = topAppBarColors,
            scrollBehavior = scrollBehavior,
            navigationIcon = navigationIconContent,
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.testTag("PageTitleLabel"),
                    onTextLayout = {
                        titleTextHasOverflow = it.hasVisualOverflow
                    },
                )
            },
            modifier = modifier.testTag("HeaderBarComponent"),
            actions = actions,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("LongMethod")
@Composable
fun AppTopAppBar(
    title: String,
    subtitle: String,
    scrollBehavior: TopAppBarScrollBehavior,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String,
    onNavigationIconClick: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
) {
    val navigationIconContent: @Composable () -> Unit =
        remember(navigationIcon) {
            {
                IconButton(
                    onClick = onNavigationIconClick,
                    modifier = Modifier.testTag("CloseButton"),
                ) {
                    Icon(
                        modifier = Modifier.mirrorIfRtl(),
                        imageVector = navigationIcon,
                        contentDescription = navigationIconContentDescription,
                    )
                }
            }
        }

    val topAppBarColors =
        TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        )

    LargeTopAppBar(
        colors = topAppBarColors,
        scrollBehavior = scrollBehavior,
        navigationIcon = navigationIconContent,
        title = {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                // The height of the component is controlled and will only allow for 1 extra row,
                // making adding any arguments for softWrap and minLines superfluous.
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.testTag("PageTitleLabel"),
                )

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.testTag("PageTitleSubTitle"),
                )
            }
        },
        modifier = modifier.testTag("HeaderBarComponent"),
        actions = actions,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun AppTopAppBar_preview() {
    AppTheme {
        AppTopAppBar(
            title = "Title",
            scrollBehavior =
            TopAppBarDefaults
                .exitUntilCollapsedScrollBehavior(
                    rememberTopAppBarState(),
                ),
            navigationIcon =
            NavigationIcon(
                navigationIcon = AppIcons.Back,
                navigationIconContentDescription = "Back",
                onNavigationIconClick = { },
            ),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun AppTopAppBarOverflow_preview() {
    AppTheme {
        AppTopAppBar(
            title = "Title that is too long for the top line",
            scrollBehavior =
            TopAppBarDefaults
                .exitUntilCollapsedScrollBehavior(
                    rememberTopAppBarState(),
                ),
            navigationIcon =
            NavigationIcon(
                navigationIcon = AppIcons.Close,
                navigationIconContentDescription = "Close",
                onNavigationIconClick = { },
            ),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun AppTopAppBarOverflowCutoff_preview() {
    AppTheme {
        AppTopAppBar(
            title = "Title that is too long for the top line and the bottom line",
            scrollBehavior =
            TopAppBarDefaults
                .exitUntilCollapsedScrollBehavior(
                    rememberTopAppBarState(),
                ),
            navigationIcon =
            NavigationIcon(
                navigationIcon = AppIcons.Close,
                navigationIconContentDescription = "Close",
                onNavigationIconClick = { },
            ),
        )
    }
}

data class NavigationIcon(
    val navigationIcon: ImageVector,
    val navigationIconContentDescription: String,
    val onNavigationIconClick: () -> Unit,
)

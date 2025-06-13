/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    backPress: () -> Unit,
    modifier: Modifier = Modifier,
    topBarTitle: String? = null,
    floatingActionButtonContent: FloatingActionButtonContent? = null,
    pullToRefreshState: AppPullToRefreshState = rememberAppPullToRefreshState(),
    snackbarHost: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit = {},
) {
    Scaffold(
        topBar = {
            if (topBarTitle != null) {
                AppTopBar(
                    topBarTitle = topBarTitle,
                    backPress = backPress,
                    actions = actions,
                )
            }
        },
        floatingActionButton = {
            floatingActionButtonContent?.let { content ->
                FloatingActionButton(
                    onClick = content.onClick,
                    contentColor = content.contentColor,
                    content = content.content,
                )
            }
        },
        snackbarHost = snackbarHost,
        containerColor = Color.Transparent,
        content = { paddingValues ->
            val internalPullToRefreshState = rememberPullToRefreshState()
            Box(
                modifier =
                Modifier.pullToRefresh(
                    state = internalPullToRefreshState,
                    isRefreshing = pullToRefreshState.isRefreshing,
                    onRefresh = pullToRefreshState.onRefresh,
                    enabled = pullToRefreshState.isEnabled,
                ),
            ) {
                content(paddingValues)

                PullToRefreshDefaults.Indicator(
                    modifier =
                    Modifier
                        .padding(paddingValues)
                        .align(Alignment.TopCenter),
                    isRefreshing = pullToRefreshState.isRefreshing,
                    state = internalPullToRefreshState,
                )
            }
        },
        modifier =
        modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .imePadding(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    pullToRefreshState: AppPullToRefreshState = rememberAppPullToRefreshState(),
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = Color.Transparent,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier =
        modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .imePadding(),
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            Box(modifier = Modifier.navigationBarsPadding()) {
                floatingActionButton()
            }
        },
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentColor = contentColor,
        contentWindowInsets = contentWindowInsets,
        content = { paddingValues ->
            val internalPullToRefreshState = rememberPullToRefreshState()
            Box(
                modifier =
                Modifier.pullToRefresh(
                    state = internalPullToRefreshState,
                    isRefreshing = pullToRefreshState.isRefreshing,
                    onRefresh = pullToRefreshState.onRefresh,
                    enabled = pullToRefreshState.isEnabled,
                ),
            ) {
                content(paddingValues)

                PullToRefreshDefaults.Indicator(
                    modifier =
                    Modifier
                        .padding(paddingValues)
                        .align(Alignment.TopCenter),
                    isRefreshing = pullToRefreshState.isRefreshing,
                    state = internalPullToRefreshState,
                )
            }
        },
    )
}

data class FloatingActionButtonContent(
    val onClick: (() -> Unit),
    val contentColor: Color,
    val content: (@Composable () -> Unit),
)

data class AppPullToRefreshState(
    val isEnabled: Boolean,
    val isRefreshing: Boolean,
    val onRefresh: () -> Unit,
)

@Composable
fun rememberAppPullToRefreshState(
    isEnabled: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = { },
): AppPullToRefreshState =
    remember(isEnabled, isRefreshing, onRefresh) {
        AppPullToRefreshState(
            isEnabled = isEnabled,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
        )
    }

/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package id.co.appnavigation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import app.navigation.generated.resources.Res
import app.navigation.generated.resources.app_name
import app.navigation.generated.resources.ask_anything
import app.navigation.generated.resources.new_chat
import app.navigation.generated.resources.not_connected
import id.co.appnavigation.ui.component.AppMainDrawer
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import neurochat.core.designsystem.component.AppOutlinedTextField
import neurochat.core.designsystem.component.AppText
import neurochat.core.designsystem.component.AppTextButton
import neurochat.core.designsystem.icon.AppIcons
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun App(
    state: MainState,
    isOffline: Boolean,
    isCompact: Boolean,
    modifier: Modifier = Modifier,
) {
    var askMe by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val drawerInit = if (isCompact) DrawerValue.Closed else DrawerValue.Open

    val snackBarHostState = remember { SnackbarHostState() }

    val drawerState = rememberDrawerState(initialValue = drawerInit)

    val notConnectedMessage = stringResource(Res.string.not_connected)

    if (isOffline) {
        LaunchedEffect(Unit) {
            snackBarHostState.showSnackbar(
                message = notConnectedMessage,
                duration = Indefinite,
            )
        }
    }

    AppMainDrawer(
        modifier = modifier,
        histories = if (state is MainState.HistoryLoaded) state.histories else persistentListOf(),
        onHistoryClick = {},
        drawerState = drawerState,
        content = {
            MainContent(
                snackBarHostState = snackBarHostState,
                askMe = askMe,
                onAskMeChange = { askMe = it },
                onMenuClick = {
                    scope.launch {
                        drawerState.toggle()
                    }
                },
            )
        },
    )
}

suspend fun DrawerState.toggle() {
    if (isOpen) close() else open()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    title: String,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(
                onClick = onMenuClick,
                content = {
                    Icon(AppIcons.ChatBubble, contentDescription = null)
                },
            )
        },
        actions = {
            AppTextButton(
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(AppIcons.Add, contentDescription = null)
                        AppText(stringResource(Res.string.new_chat))
                    }
                },
                onClick = {},
            )
        },
        colors =
        TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
        modifier = modifier.testTag("ilmiTopAppBar"),
    )
}

@Composable
private fun MainContent(
    snackBarHostState: SnackbarHostState,
    onMenuClick: () -> Unit,
    askMe: String,
    onAskMeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.navigationBarsPadding(),
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            AppBar(
                title = stringResource(Res.string.app_name),
                onMenuClick = onMenuClick,
            )
        },
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Vertical,
                    ),
                ),
        ) {
            MainChatSection(modifier = Modifier.weight(1f))
            ChatTextBox(askMe, onAskMeChange, modifier = Modifier.padding(16.dp))
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun MainChatSection(modifier: Modifier = Modifier) {
    Box(modifier = modifier)
}

@Composable
private fun ChatTextBox(
    askMe: String,
    onAskMeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    AppOutlinedTextField(
        value = askMe,
        onValueChange = onAskMeChange,
        label = stringResource(Res.string.ask_anything),
        modifier = modifier,
        trailingIcon = {
            Row {
                IconButton(
                    onClick = {},
                ) {
                    Icon(AppIcons.Mic, contentDescription = null)
                }
                IconButton(
                    onClick = {},
                ) {
                    Icon(AppIcons.Send, contentDescription = null)
                }
            }
        },
        leadingIcon = {
            IconButton(
                onClick = {},
            ) {
                Icon(AppIcons.Add, contentDescription = null)
            }
        },
    )
}

/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package id.co.appnavigation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.navigation.generated.resources.Res
import app.navigation.generated.resources.chat_history
import id.co.appnavigation.ui.data.ChatHistory
import id.co.appnavigation.ui.data.History
import kotlinx.collections.immutable.ImmutableList
import neurochat.core.component.CircularProfileImage
import neurochat.core.designsystem.component.AppDrawerItem
import neurochat.core.designsystem.component.AppDrawerSheet
import neurochat.core.designsystem.component.AppNavigationDrawer
import neurochat.core.designsystem.component.AppText
import neurochat.core.designsystem.icon.AppIcons
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppMainDrawer(
    histories: ImmutableList<ChatHistory>,
    onHistoryClick: (History) -> Unit,
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    AppNavigationDrawer(
        modifier = modifier,
        state = drawerState,
        drawerContent = {
            MainDrawerContent(histories, onHistoryClick)
        },
        content = content,
    )
}

@Composable
private fun MainDrawerContent(
    histories: ImmutableList<ChatHistory>,
    onHistoryClick: (History) -> Unit,
) {
    AppDrawerSheet {
        Column {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp).weight(1f),
            ) {
                item {
                    Spacer(Modifier.height(12.dp))
                    AppText(
                        stringResource(Res.string.chat_history),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge,
                    )
                    HorizontalDivider()
                }
                items(histories, key = { it.id }) {
                    AppText(
                        modifier = Modifier.padding(16.dp),
                        text = it.title,
                        style = MaterialTheme.typography.titleSmall,
                    )
                    it.history.forEach { history ->
                        AppDrawerItem(
                            label = { AppText(history.title) },
                            selected = false,
                            onClick = { onHistoryClick(history) },
                        )
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp),
            ) {
                CircularProfileImage(
                    url = "https://media.licdn.com/dms/image/v2/D5603AQFESyOkUG0bUA/profile-displayphoto-shrink_800_800/B56ZamQVM6HsAc-/0/1746546022308?e=1755734400&v=beta&t=TwAqVzIroCmi3pSn_myI-5Gf_2AqumXW70w0uTOPyG0",
                )
                Spacer(Modifier.width(8.dp))
                AppText("Rizki Rakasiwi")
                Spacer(Modifier.weight(1f))
                IconButton(
                    onClick = {},
                ) {
                    Icon(AppIcons.Settings, contentDescription = null)
                }
            }
        }
    }
}

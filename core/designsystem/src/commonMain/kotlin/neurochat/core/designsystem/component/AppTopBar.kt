/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import neurochat.core.designsystem.icon.AppIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    topBarTitle: String,
    backPress: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = topBarTitle,
                style = MaterialTheme.typography.titleMedium,
            )
        },
        navigationIcon = {
            IconButton(
                onClick = backPress,
            ) {
                Icon(
                    imageVector = AppIcons.ArrowBack2,
                    contentDescription = "Back",
                )
            }
        },
        colors =
        TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
        ),
        actions = actions,
        modifier = modifier,
    )
}

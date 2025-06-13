/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import neurochat.core.designsystem.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AppLoadingDialog(
    visibilityState: LoadingDialogState,
    modifier: Modifier = Modifier,
) {
    when (visibilityState) {
        is LoadingDialogState.Hidden -> Unit
        is LoadingDialogState.Shown -> {
            Dialog(
                onDismissRequest = {},
                properties =
                    DialogProperties(
                        dismissOnBackPress = false,
                        dismissOnClickOutside = false,
                    ),
            ) {
                Card(
                    shape = RoundedCornerShape(28.dp),
                    colors =
                        CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                        ),
                    modifier =
                        modifier
                            .semantics {
                                testTag = "AlertPopup"
                            }
                            .fillMaxWidth()
                            .wrapContentHeight(),
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Loading..",
                            modifier =
                                Modifier
                                    .testTag("AlertTitleText")
                                    .padding(
                                        top = 24.dp,
                                        bottom = 8.dp,
                                    ),
                        )
                        CircularProgressIndicator(
                            modifier =
                                Modifier
                                    .testTag("AlertProgressIndicator")
                                    .padding(
                                        top = 8.dp,
                                        bottom = 24.dp,
                                    ),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun AppLoadingDialog_preview() {
    AppTheme {
        AppLoadingDialog(
            visibilityState = LoadingDialogState.Shown,
        )
    }
}

/**
 * Models display of a [AppLoadingDialog].
 */
sealed class LoadingDialogState {
    data object Hidden : LoadingDialogState()

    data object Shown : LoadingDialogState()
}

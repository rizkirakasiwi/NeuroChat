/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.designsystem.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import neurochat.core.designsystem.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AppBasicDialog(
    visibilityState: BasicDialogState,
    onDismissRequest: () -> Unit,
): Unit =
    when (visibilityState) {
        BasicDialogState.Hidden -> Unit
        is BasicDialogState.Shown -> {
            AlertDialog(
                onDismissRequest = onDismissRequest,
                confirmButton = {
                    AppTextButton(
                        content = {
                            Text(text = "Ok")
                        },
                        onClick = onDismissRequest,
                        modifier = Modifier.testTag("AcceptAlertButton"),
                    )
                },
                title =
                visibilityState.title.let {
                    {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.testTag("AlertTitleText"),
                        )
                    }
                },
                text = {
                    Text(
                        text = visibilityState.message,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.testTag("AlertContentText"),
                    )
                },
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                modifier =
                Modifier.semantics {
                    testTag = "AlertPopup"
                },
            )
        }
    }

@Composable
fun AppBasicDialog(
    visibilityState: BasicDialogState,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit,
): Unit =
    when (visibilityState) {
        BasicDialogState.Hidden -> Unit
        is BasicDialogState.Shown -> {
            AlertDialog(
                onDismissRequest = onDismissRequest,
                confirmButton = {
                    AppTextButton(
                        content = {
                            Text(text = "Ok")
                        },
                        onClick = onConfirm,
                        modifier = Modifier.testTag("AcceptAlertButton"),
                    )
                },
                dismissButton = {
                    AppTextButton(
                        content = {
                            Text(text = "Cancel")
                        },
                        onClick = onDismissRequest,
                        modifier = Modifier.testTag("DismissAlertButton"),
                    )
                },
                title =
                visibilityState.title.let {
                    {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.testTag("AlertTitleText"),
                        )
                    }
                },
                text = {
                    Text(
                        text = visibilityState.message,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.testTag("AlertContentText"),
                    )
                },
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                modifier =
                Modifier.semantics {
                    testTag = "AlertPopup"
                },
            )
        }
    }

@Preview
@Composable
private fun AppBasicDialog_preview() {
    AppTheme {
        AppBasicDialog(
            visibilityState =
            BasicDialogState.Shown(
                title = "An error has occurred.",
                message = "Username or password is incorrect. Try again.",
            ),
            onDismissRequest = {},
        )
    }
}

/**
 * Models display of a [AppBasicDialog].
 */
sealed class BasicDialogState {
    data object Hidden : BasicDialogState()

    data class Shown(
        val message: String,
        val title: String = "An Error Occurred!",
    ) : BasicDialogState()
}

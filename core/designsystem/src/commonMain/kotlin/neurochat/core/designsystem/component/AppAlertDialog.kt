/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.designsystem.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppDialogBox(
    title: String,
    showDialogState: Boolean,
    confirmButtonText: String,
    dismissButtonText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    message: String? = null,
) {
    if (showDialogState) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = onDismiss,
            title = { Text(text = title) },
            text = {
                if (message != null) {
                    Text(text = message)
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirm()
                    },
                ) {
                    Text(text = confirmButtonText)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = dismissButtonText)
                }
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IlmiCustomDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        content = content,
        modifier = modifier,
    )
}

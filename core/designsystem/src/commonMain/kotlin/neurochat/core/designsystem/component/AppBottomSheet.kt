/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.essenty.backhandler.BackCallback
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(true) }

    fun dismissSheet() {
        coroutineScope.launch { modalSheetState.hide() }.invokeOnCompletion {
            if (!modalSheetState.isVisible) {
                showBottomSheet = false
            }
        }
        onDismiss.invoke()
    }

    BackCallback(modalSheetState.isVisible) {
        dismissSheet()
    }

    AnimatedVisibility(visible = showBottomSheet) {
        ModalBottomSheet(
            containerColor = Color.White,
            onDismissRequest = {
                showBottomSheet = false
                dismissSheet()
            },
            sheetState = modalSheetState,
            modifier = modifier,
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun AppBottomSheetPreview() {
    AppBottomSheet(
        content = {
            Box {
                Modifier.height(100.dp)
            }
        },
        onDismiss = {},
    )
}

/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.designsystem.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import neurochat.core.designsystem.icon.AppIcons

@Composable
@Suppress("LongParameterList")
fun AppOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    showClearIcon: Boolean = true,
    readOnly: Boolean = false,
    clearIcon: ImageVector = AppIcons.Close,
    isError: Boolean = false,
    errorText: String? = null,
    onClickClearIcon: () -> Unit = { onValueChange("") },
    textStyle: TextStyle = LocalTextStyle.current,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    val isFocused by interactionSource.collectIsFocusedAsState()
    val showIcon by rememberUpdatedState(value.isNotEmpty())

    OutlinedTextField(
        value = value,
        label = { Text(text = label) },
        onValueChange = onValueChange,
        textStyle = textStyle,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        readOnly = readOnly,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        leadingIcon = leadingIcon,
        isError = isError,
        trailingIcon = @Composable {
            AnimatedContent(
                targetState = showClearIcon && isFocused && showIcon,
            ) {
                if (it) {
                    ClearIconButton(
                        showClearIcon = true,
                        clearIcon = clearIcon,
                        onClickClearIcon = onClickClearIcon,
                    )
                } else {
                    trailingIcon?.invoke()
                }
            }
        },
        supportingText =
            errorText?.let {
                {
                    Text(
                        modifier = Modifier.testTag("errorTag"),
                        text = it,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            },
    )
}

@Composable
@Suppress("LongParameterList")
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    showClearIcon: Boolean = true,
    readOnly: Boolean = false,
    clearIcon: ImageVector = AppIcons.Close,
    isError: Boolean = false,
    errorText: String? = null,
    onClickClearIcon: () -> Unit = { onValueChange("") },
    textStyle: TextStyle = LocalTextStyle.current,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    val isFocused by interactionSource.collectIsFocusedAsState()
    val showIcon by rememberUpdatedState(value.isNotEmpty())

    OutlinedTextField(
        value = value,
        label = { Text(text = label) },
        onValueChange = onValueChange,
        textStyle = textStyle,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        readOnly = readOnly,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        leadingIcon = leadingIcon,
        isError = isError,
        trailingIcon = @Composable {
            AnimatedContent(
                targetState = showClearIcon && isFocused && showIcon,
            ) {
                if (it) {
                    ClearIconButton(
                        showClearIcon = true,
                        clearIcon = clearIcon,
                        onClickClearIcon = onClickClearIcon,
                    )
                } else {
                    trailingIcon?.invoke()
                }
            }
        },
        supportingText =
            errorText?.let {
                {
                    Text(
                        modifier = Modifier.testTag("errorTag"),
                        text = it,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            },
    )
}

@Composable
private fun ClearIconButton(
    showClearIcon: Boolean,
    clearIcon: ImageVector,
    onClickClearIcon: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = showClearIcon,
        modifier = modifier,
    ) {
        IconButton(
            onClick = onClickClearIcon,
            modifier =
                Modifier.semantics {
                    contentDescription = "clearIcon"
                },
        ) {
            Icon(
                imageVector = clearIcon,
                contentDescription = "trailingIcon",
            )
        }
    }
}

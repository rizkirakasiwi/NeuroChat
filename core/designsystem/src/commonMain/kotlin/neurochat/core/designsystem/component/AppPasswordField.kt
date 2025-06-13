/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.designsystem.component

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import neurochat.core.designsystem.icon.AppIcons
import neurochat.core.designsystem.utils.nonLetterColorVisualTransformation
import neurochat.core.designsystem.utils.tabNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AppPasswordField(
    label: String,
    value: String,
    showPassword: Boolean,
    showPasswordChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    hint: String? = null,
    showPasswordTestTag: String? = null,
    autoFocus: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Password,
    imeAction: ImeAction = ImeAction.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val focusRequester = remember { FocusRequester() }
    AppOutlinedTextField(
        modifier =
            modifier
                .tabNavigation()
                .focusRequester(focusRequester),
        label = label,
        value = value,
        onValueChange = onValueChange,
        visualTransformation =
            when {
                !showPassword -> PasswordVisualTransformation()
                readOnly -> nonLetterColorVisualTransformation()
                else -> VisualTransformation.None
            },
        singleLine = singleLine,
        readOnly = readOnly,
        keyboardOptions =
            KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction,
            ),
        keyboardActions = keyboardActions,
        errorText = hint,
        trailingIcon = {
            IconButton(
                onClick = { showPasswordChange.invoke(!showPassword) },
            ) {
                val imageVector =
                    if (showPassword) {
                        AppIcons.OutlinedVisibilityOff
                    } else {
                        AppIcons.OutlinedVisibility
                    }

                Icon(
                    modifier = Modifier.semantics { showPasswordTestTag?.let { testTag = it } },
                    imageVector = imageVector,
                    contentDescription = "togglePassword",
                )
            }
        },
    )
    if (autoFocus) {
        LaunchedEffect(Unit) { focusRequester.requestFocus() }
    }
}

@Composable
fun AppPasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    hint: String? = null,
    initialShowPassword: Boolean = false,
    showPasswordTestTag: String? = null,
    autoFocus: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Password,
    imeAction: ImeAction = ImeAction.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    var showPassword by rememberSaveable { mutableStateOf(initialShowPassword) }
    AppPasswordField(
        modifier = modifier,
        label = label,
        value = value,
        showPassword = showPassword,
        showPasswordChange = { showPassword = !showPassword },
        onValueChange = onValueChange,
        readOnly = readOnly,
        singleLine = singleLine,
        hint = hint,
        showPasswordTestTag = showPasswordTestTag,
        autoFocus = autoFocus,
        keyboardType = keyboardType,
        imeAction = imeAction,
        keyboardActions = keyboardActions,
    )
}

@Preview
@Composable
private fun AppPasswordField_preview_withInput_hidePassword() {
    AppPasswordField(
        label = "Label",
        value = "Password",
        onValueChange = {},
        initialShowPassword = false,
        hint = "Hint",
    )
}

@Preview
@Composable
private fun AppPasswordField_preview_withInput_showPassword() {
    AppPasswordField(
        label = "Label",
        value = "Password",
        onValueChange = {},
        initialShowPassword = true,
        hint = "Hint",
    )
}

@Preview
@Composable
private fun AppPasswordField_preview_withoutInput_hidePassword() {
    AppPasswordField(
        label = "Label",
        value = "",
        onValueChange = {},
        initialShowPassword = false,
        hint = "Hint",
    )
}

@Preview
@Composable
private fun AppPasswordField_preview_withoutInput_showPassword() {
    AppPasswordField(
        label = "Label",
        value = "",
        onValueChange = {},
        initialShowPassword = true,
        hint = "Hint",
    )
}

/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AppOtpTextField(
    onOtpTextCorrectlyEnter: () -> Unit,
    modifier: Modifier = Modifier,
    realOtp: String = "",
    otpCount: Int = 4,
) {
    var otpText by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BasicTextField(
            modifier = Modifier,
            value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
            onValueChange = {
                otpText = it.text
                isError = false
                if (otpText.length == otpCount) {
                    if (otpText != realOtp) {
                        isError = true
                    } else {
                        onOtpTextCorrectlyEnter.invoke()
                    }
                }
            },
            keyboardActions =
                KeyboardActions(
                    onDone = {
                        if (otpText != realOtp) {
                            isError = true
                        } else {
                            onOtpTextCorrectlyEnter.invoke()
                        }
                        println("OTP: $otpText and $isError")
                    },
                ),
            keyboardOptions =
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                ),
            decorationBox = {
                Row(horizontalArrangement = Arrangement.Center) {
                    repeat(otpCount) { index ->
                        CharView(
                            index = index,
                            text = otpText,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            },
        )
        if (isError) {
            // display erro message in text
            Text(
                text = "Invalid OTP",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}

@Composable
private fun CharView(
    index: Int,
    text: String,
    modifier: Modifier = Modifier,
) {
    val isFocused = text.length == index
    val char =
        when {
            index == text.length -> "_"
            index > text.length -> "_"
            else -> text[index].toString()
        }
    Text(
        modifier =
            modifier
                .width(40.dp)
                .wrapContentHeight(align = Alignment.CenterVertically),
        text = char,
        style = MaterialTheme.typography.headlineSmall,
        color =
            if (isFocused) {
                Color.DarkGray
            } else {
                Color.LightGray
            },
        textAlign = TextAlign.Center,
    )
}

@Preview
@Composable
private fun PreviewOtpTextField() {
    AppOtpTextField(
        onOtpTextCorrectlyEnter = {},
        realOtp = "1234",
        otpCount = 4,
    )
}

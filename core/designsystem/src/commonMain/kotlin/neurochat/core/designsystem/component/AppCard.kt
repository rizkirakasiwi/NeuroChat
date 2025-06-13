/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    elevation: Dp = 1.dp,
    onClick: (() -> Unit)? = null,
    colors: CardColors = CardDefaults.cardColors(),
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        shape = shape,
        modifier =
        modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        elevation =
        CardDefaults.cardElevation(
            defaultElevation = elevation,
        ),
        colors = colors,
        content = content,
    )
}

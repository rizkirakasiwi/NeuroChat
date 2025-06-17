/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import neurochat.core.ui.generated.resources.Res
import neurochat.core.ui.generated.resources.profile_placeholder
import neurochat.core.ui.rememberImageLoader
import neurochat.core.ui.rememberImageRequest
import org.jetbrains.compose.resources.painterResource

@Composable
fun CircularProfileImage(url: String, modifier: Modifier = Modifier, size: Dp = 30.dp) {
    val context = LocalPlatformContext.current
    val imageLoader = rememberImageLoader(context)

    AsyncImage(
        model = rememberImageRequest(context, url),
        contentDescription = "Profile picture",
        imageLoader = imageLoader,
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(Res.drawable.profile_placeholder),
        error = painterResource(Res.drawable.profile_placeholder),
    )
}

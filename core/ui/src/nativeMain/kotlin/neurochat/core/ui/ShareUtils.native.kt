/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.ui

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import io.github.vinceglb.filekit.core.FileKit
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

actual object ShareUtils {
    actual fun shareText(text: String) {
        val currentViewController = UIApplication.sharedApplication().keyWindow?.rootViewController
        val activityViewController = UIActivityViewController(listOf(text), null)
        currentViewController?.presentViewController(
            viewControllerToPresent = activityViewController,
            animated = true,
            completion = null,
        )
    }

    actual suspend fun shareImage(
        title: String,
        image: ImageBitmap,
    ) {
        FileKit.saveFile(
            bytes = image.asSkiaBitmap().readPixels(),
            baseName = title,
            extension = "png",
        )
    }

    actual suspend fun shareImage(
        title: String,
        byte: ByteArray,
    ) {
        FileKit.saveFile(
            bytes = byte,
            baseName = title,
            extension = "png",
        )
    }
}

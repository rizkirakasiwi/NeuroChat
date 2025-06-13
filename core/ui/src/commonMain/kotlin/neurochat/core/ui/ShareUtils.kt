/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.ui

import androidx.compose.ui.graphics.ImageBitmap

/**
 * Platform-specific utility for sharing content with other applications.
 * This expect class requires platform-specific implementations.
 */
expect object ShareUtils {
    /**
     * Shares text content with other applications.
     *
     * @param text The text content to be shared
     */
    fun shareText(text: String)

    /**
     * Shares an image with other applications.
     *
     * @param title The title to use when sharing the image
     * @param image The ImageBitmap to be shared
     */
    suspend fun shareImage(
        title: String,
        image: ImageBitmap,
    )

    /**
     * Shares an image with other applications using raw byte data.
     *
     * @param title The title to use when sharing the image
     * @param byte The raw image data as ByteArray
     */
    suspend fun shareImage(
        title: String,
        byte: ByteArray,
    )
}

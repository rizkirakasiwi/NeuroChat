/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.platform.intent

import androidx.compose.ui.graphics.ImageBitmap
import neurochat.core.platform.model.MimeType

class IntentManagerImpl : IntentManager {
    override fun startActivity(intent: Any) {
        // TODO("Not yet implemented")
    }

    override fun launchUri(uri: String) {
        // TODO("Not yet implemented")
    }

    override fun shareText(text: String) {
        // TODO("Not yet implemented")
    }

    override fun shareFile(
        fileUri: String,
        mimeType: MimeType,
    ) {
        // TODO("Not yet implemented")
    }

    override fun shareFile(
        fileUri: String,
        mimeType: MimeType,
        extraText: String,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun shareImage(
        title: String,
        image: ImageBitmap,
    ) {
        TODO("Not yet implemented")
    }

    override fun createDocumentIntent(fileName: String): Any {
        // TODO("Not yet implemented")
        return Any()
    }

    override fun startApplicationDetailsSettingsActivity() {
        // TODO("Not yet implemented")
    }
}

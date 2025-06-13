/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

actual object ShareUtils {
    private var activityProvider: () -> Activity = {
        throw IllegalArgumentException(
            "You need to implement the 'activityProvider' to provide the required Activity. " +
                "Just make sure to set a valid activity using " +
                "the 'setActivityProvider()' method.",
        )
    }

    fun setActivityProvider(provider: () -> Activity) {
        activityProvider = provider
    }

    actual fun shareText(text: String) {
        val intent =
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
        val intentChooser = Intent.createChooser(intent, null)
        activityProvider.invoke().startActivity(intentChooser)
    }

    actual suspend fun shareImage(
        title: String,
        image: ImageBitmap,
    ) {
        val context = activityProvider.invoke().application.baseContext

        val uri = saveImage(image.asAndroidBitmap(), context)

        val sendIntent: Intent =
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, uri)
                setDataAndType(uri, "image/png")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

        val shareIntent = Intent.createChooser(sendIntent, title)
        activityProvider.invoke().startActivity(shareIntent)
    }

    @OptIn(ExperimentalResourceApi::class)
    actual suspend fun shareImage(
        title: String,
        byte: ByteArray,
    ) {
        val context = activityProvider.invoke().application.baseContext
        val imageBitmap = byte.decodeToImageBitmap()

        val uri = saveImage(imageBitmap.asAndroidBitmap(), context)

        val sendIntent: Intent =
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, uri)
                setDataAndType(uri, "image/png")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

        val shareIntent = Intent.createChooser(sendIntent, title)
        activityProvider.invoke().startActivity(shareIntent)
    }

    private const val IMAGE_QUALITY = 100

    private suspend fun saveImage(
        image: Bitmap,
        context: Context,
    ): Uri? = withContext(Dispatchers.IO) {
        try {
            val imagesFolder = File(context.cacheDir, "images")
            imagesFolder.mkdirs()
            val file = File(imagesFolder, "shared_image.png")

            val stream = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.PNG, IMAGE_QUALITY, stream)
            stream.flush()
            stream.close()

            FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        } catch (e: IOException) {
            Log.d("saving bitmap", "saving bitmap error ${e.message}")
            null
        }
    }
}

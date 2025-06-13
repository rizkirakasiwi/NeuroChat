/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package id.co.appandroid

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import id.co.appshared.utils.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

/**
 * Android application class.
 * This class is used to initialize Koin modules for dependency injection in the Android application.
 * It sets up the Koin framework, providing the necessary dependencies for the app.
 *
 * @constructor Create empty Android app
 * @see Application
 */
class AndroidApp :
    Application(),
    SingletonImageLoader.Factory {
    companion object {
        private const val MAX_SIZE_PERCENT = 0.25
    }
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@AndroidApp) // Provides the Android app context
            androidLogger() // Enables Koin's logging for debugging
        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader = ImageLoader
        .Builder(context)
        .networkCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizePercent(MAX_SIZE_PERCENT)
                .build()
        }
        .memoryCache {
            MemoryCache.Builder()
                .maxSizePercent(context, MAX_SIZE_PERCENT)
                .build()
        }
        .crossfade(true)
        .build()
}

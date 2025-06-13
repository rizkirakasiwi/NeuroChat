/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.data.di

import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import neurochat.core.data.util.ConnectivityManagerNetworkMonitor
import neurochat.core.data.util.TimeZoneBroadcastMonitor
import neurochat.core.data.utils.NetworkMonitor
import neurochat.core.data.utils.TimeZoneMonitor

class AndroidPlatformDependentDataModule(
    private val context: Context,
    private val dispatcher: CoroutineDispatcher,
    private val scope: CoroutineScope,
) : PlatformDependentDataModule {
    override val networkMonitor: NetworkMonitor by lazy {
        ConnectivityManagerNetworkMonitor(context, dispatcher)
    }

    override val timeZoneMonitor: TimeZoneMonitor by lazy {
        TimeZoneBroadcastMonitor(context, scope, dispatcher)
    }
}

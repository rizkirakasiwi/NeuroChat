/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.data.di

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.TimeZone
import neurochat.core.data.utils.NetworkMonitor
import neurochat.core.data.utils.TimeZoneMonitor

class NativePlatformDependentDataModule : PlatformDependentDataModule {
    override val networkMonitor: NetworkMonitor by lazy {
        object : NetworkMonitor {
            override val isOnline: Flow<Boolean> = flowOf(true)
        }
    }

    override val timeZoneMonitor: TimeZoneMonitor by lazy {
        object : TimeZoneMonitor {
            override val currentTimeZone: Flow<TimeZone> = flowOf(TimeZone.currentSystemDefault())
        }
    }
}

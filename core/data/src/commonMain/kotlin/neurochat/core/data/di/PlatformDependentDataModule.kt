/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.data.di

import neurochat.core.data.utils.NetworkMonitor
import neurochat.core.data.utils.TimeZoneMonitor
import org.koin.core.module.Module

interface PlatformDependentDataModule {
    val networkMonitor: NetworkMonitor

    val timeZoneMonitor: TimeZoneMonitor
}

expect val platformModule: Module

expect val getPlatformDataModule: PlatformDependentDataModule

/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.data.di

import neurochat.core.data.utils.NetworkMonitor
import neurochat.core.data.utils.TimeZoneMonitor
import org.koin.dsl.module

val DataModule =
    module {
        includes(platformModule)
        single<PlatformDependentDataModule> { getPlatformDataModule }
        single<NetworkMonitor> { getPlatformDataModule.networkMonitor }
        single<TimeZoneMonitor> { getPlatformDataModule.timeZoneMonitor }
    }

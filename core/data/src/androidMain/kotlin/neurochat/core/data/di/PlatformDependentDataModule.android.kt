/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.data.di

import neurochat.core.common.di.AppDispatchers
import neurochat.core.data.util.ConnectivityManagerNetworkMonitor
import neurochat.core.data.util.TimeZoneBroadcastMonitor
import neurochat.core.data.utils.NetworkMonitor
import neurochat.core.data.utils.TimeZoneMonitor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val AndroidDataModule =
    module {
        single<NetworkMonitor> {
            ConnectivityManagerNetworkMonitor(androidContext(), get(named(AppDispatchers.IO.name)))
        }

        single<TimeZoneMonitor> {
            TimeZoneBroadcastMonitor(
                context = androidContext(),
                appScope = get(named("ApplicationScope")),
                ioDispatcher = get(named(AppDispatchers.IO.name)),
            )
        }

        single {
            AndroidPlatformDependentDataModule(
                context = androidContext(),
                dispatcher = get(named(AppDispatchers.IO.name)),
                scope = get(named("ApplicationScope")),
            )
        }
    }

actual val platformModule: Module = AndroidDataModule

actual val getPlatformDataModule: PlatformDependentDataModule
    get() = org.koin.core.context.GlobalContext.get().get()

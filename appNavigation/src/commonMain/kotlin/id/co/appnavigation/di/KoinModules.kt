/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package id.co.appnavigation.di

import id.co.appnavigation.AppViewModel
import id.co.appnavigation.ui.MainViewModel
import neurochat.core.common.di.DispatchersModule
import neurochat.core.data.di.DataModule
import neurochat.core.datastore.di.DatastoreModule
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

object KoinModules {
    private val dataModule =
        module {
            includes(DataModule)
        }

    private val dispatcherModule =
        module {
            includes(DispatchersModule)
        }

    private val AppModule =
        module {
            viewModelOf(::AppViewModel)
            viewModelOf(::MainViewModel)
        }

    val allModules =
        listOf(
            dataModule,
            DatastoreModule,
            dispatcherModule,
            AppModule,
        )
}

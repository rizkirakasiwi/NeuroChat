/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.datastore.core.di

import com.russhwolf.settings.Settings
import neurochat.core.common.di.AppDispatchers
import neurochat.core.datastore.core.ReactivePreferencesRepository
import neurochat.core.datastore.core.contracts.ReactiveDataStore
import neurochat.core.datastore.core.factory.DataStoreFactory
import neurochat.core.datastore.core.reactive.PreferenceFlowOperators
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Koin module for providing core datastore dependencies.
 *
 * Usage Example:
 * ```kotlin
 * startKoin {
 *     modules(CoreDatastoreModule)
 * }
 * ```
 */
val CoreDatastoreModule =
    module {

        // Platform-specific Settings instance
        single<Settings> { Settings() }

        // Main reactive datastore repository (recommended for most use cases)
        single<ReactivePreferencesRepository> {
            DataStoreFactory()
                .settings(get())
                .dispatcher(get(named(AppDispatchers.IO.name)))
                .cacheSize(200)
                .build()
        }

        // Direct access to reactive datastore (if needed for specific use cases)
        single<ReactiveDataStore> {
            DataStoreFactory()
                .settings(get())
                .dispatcher(get(named(AppDispatchers.IO.name)))
                .cacheSize(200)
                .buildDataStore()
        }

        // Flow operators for advanced reactive operations
        single<PreferenceFlowOperators> {
            PreferenceFlowOperators(get<ReactivePreferencesRepository>())
        }
    }

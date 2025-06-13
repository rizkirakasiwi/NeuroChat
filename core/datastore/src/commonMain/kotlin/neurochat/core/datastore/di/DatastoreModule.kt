/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.datastore.di

import neurochat.core.datastore.UserPreferencesRepository
import neurochat.core.datastore.UserPreferencesRepositoryImpl
import neurochat.core.datastore.core.di.CoreDatastoreModule
import neurochat.core.datastore.core.factory.DataStoreFactory
import org.koin.dsl.module

val DatastoreModule =
    module {
        includes(CoreDatastoreModule)

        single<UserPreferencesRepository> {
            UserPreferencesRepositoryImpl(preferencesStore = DataStoreFactory.create())
        }
    }

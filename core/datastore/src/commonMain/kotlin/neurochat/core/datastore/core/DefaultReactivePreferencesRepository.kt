/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.datastore.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.serialization.KSerializer
import neurochat.core.datastore.core.contracts.DataStoreChangeEvent
import neurochat.core.datastore.core.contracts.ReactiveDataStore

/**
 * Default implementation of [ReactivePreferencesRepository] that delegates operations
 * to an underlying [ReactiveDataStore].
 *
 * This class provides the reactive repository interface by interacting with
 * a reactive data store instance.
 *
 * Example usage:
 * ```kotlin
 * // Obtain an instance, e.g., from DataStoreFactory().buildDataStore()
 * val dataStore: ReactiveDataStore = ...
 * val repository = DefaultReactivePreferencesRepository(dataStore)
 *
 * // Use repository methods
 * repository.savePreference("user_id", "123")
 * repository.observePreference("user_id", "").collect { id -> println(id) }
 * ```
 *
 * @property reactiveDataStore The underlying [ReactiveDataStore] instance.
 */
@Suppress("TooManyFunctions")
class DefaultReactivePreferencesRepository(
    private val reactiveDataStore: ReactiveDataStore,
) : ReactivePreferencesRepository {
    // Delegate base operations
    /**
     * {@inheritDoc}
     */
    override suspend fun <T> savePreference(
        key: String,
        value: T,
    ): Result<Unit> {
        println("[Repository] savePreference: key=$key, value=$value")
        return reactiveDataStore.putValue(key, value)
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun <T> getPreference(
        key: String,
        default: T,
    ): Result<T> {
        return reactiveDataStore.getValue(key, default)
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun <T> saveSerializablePreference(
        key: String,
        value: T,
        serializer: KSerializer<T>,
    ): Result<Unit> {
        return reactiveDataStore.putSerializableValue(key, value, serializer)
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun <T> getSerializablePreference(
        key: String,
        default: T,
        serializer: KSerializer<T>,
    ): Result<T> {
        return reactiveDataStore.getSerializableValue(key, default, serializer)
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun removePreference(key: String): Result<Unit> {
        println("[Repository] removePreference: key=$key")
        return reactiveDataStore.removeValue(key)
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun clearAllPreferences(): Result<Unit> {
        println("[Repository] clearAllPreferences")
        return reactiveDataStore.clearAll()
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun hasPreference(key: String): Boolean {
        return reactiveDataStore.hasKey(key).getOrDefault(false)
    }

    // Reactive operations

    /**
     * {@inheritDoc}
     */
    override fun <T> observePreference(
        key: String,
        default: T,
    ): Flow<T> {
        return reactiveDataStore.observeValue(key, default)
    }

    /**
     * {@inheritDoc}
     */
    override fun <T> observeSerializablePreference(
        key: String,
        default: T,
        serializer: KSerializer<T>,
    ): Flow<T> {
        return reactiveDataStore.observeSerializableValue(key, default, serializer)
    }

    /**
     * {@inheritDoc}
     */
    override fun observeAllKeys(): Flow<Set<String>> {
        println("[Repository] observeAllKeys: flow created")
        return reactiveDataStore.observeKeys()
            .also { println("[Repository] observeAllKeys: flow returned") }
    }

    /**
     * {@inheritDoc}
     */
    override fun observePreferenceCount(): Flow<Int> {
        return reactiveDataStore.observeSize()
    }

    /**
     * {@inheritDoc}
     */
    override fun observePreferenceChanges(): Flow<DataStoreChangeEvent> {
        return reactiveDataStore.observeChanges()
    }

    /**
     * {@inheritDoc}
     */
    override fun observePreferenceChanges(key: String): Flow<DataStoreChangeEvent> {
        return reactiveDataStore.observeChanges()
            .filter { it.key == key || it.key == "*" }
    }
}

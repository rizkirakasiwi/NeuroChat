/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.datastore.core

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer
import neurochat.core.datastore.core.contracts.DataStoreChangeEvent

/**
 * Interface for managing user preferences with reactive (Flow-based) observation capabilities.
 *
 * This interface extends [PreferencesRepository] and adds methods for observing preference values,
 * keys, and change events as Kotlin Flows, enabling reactive programming patterns.
 *
 * Example usage:
 * ```kotlin
 * val repository: ReactivePreferencesRepository = ...
 * repository.observePreference("theme", "light").collect { value -> println(value) }
 * repository.observeAllKeys().collect { keys -> println(keys) }
 * repository.observePreferenceChanges().collect { event -> println(event) }
 * ```
 */
interface ReactivePreferencesRepository : PreferencesRepository {
    /**
     * Observes the value for the specified key as a [Flow], emitting updates as they occur.
     *
     * @param key The key to observe.
     * @param default The default value to emit if the key does not exist.
     * @return A [Flow] emitting the value for the key.
     */
    fun <T> observePreference(
        key: String,
        default: T,
    ): Flow<T>

    /**
     * Observes a serializable value for the specified key as a [Flow].
     *
     * @param key The key to observe.
     * @param default The default value to emit if the key does not exist.
     * @param serializer The serializer for the value type.
     * @return A [Flow] emitting the value for the key.
     */
    fun <T> observeSerializablePreference(
        key: String,
        default: T,
        serializer: KSerializer<T>,
    ): Flow<T>

    /**
     * Observes all keys in the preferences as a [Flow], emitting updates as they occur.
     *
     * @return A [Flow] emitting the set of all keys.
     */
    fun observeAllKeys(): Flow<Set<String>>

    /**
     * Observes the number of preferences as a [Flow], emitting updates as they occur.
     *
     * @return A [Flow] emitting the number of key-value pairs in the preferences.
     */
    fun observePreferenceCount(): Flow<Int>

    /**
     * Observes all change events in the preferences as a [Flow].
     *
     * @return A [Flow] emitting [DataStoreChangeEvent] instances as changes occur.
     */
    fun observePreferenceChanges(): Flow<DataStoreChangeEvent>

    /**
     * Observes change events for a specific key as a [Flow].
     *
     * @param key The key to observe for changes.
     * @return A [Flow] emitting [DataStoreChangeEvent] instances related to the specified key.
     */
    fun observePreferenceChanges(key: String): Flow<DataStoreChangeEvent>
}

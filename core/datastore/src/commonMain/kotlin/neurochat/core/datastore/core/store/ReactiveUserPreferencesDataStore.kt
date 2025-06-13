/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.datastore.core.store

import com.russhwolf.settings.Settings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import neurochat.core.datastore.core.cache.CacheManager
import neurochat.core.datastore.core.contracts.DataStoreChangeEvent
import neurochat.core.datastore.core.contracts.ReactiveDataStore
import neurochat.core.datastore.core.handlers.TypeHandler
import neurochat.core.datastore.core.reactive.ChangeNotifier
import neurochat.core.datastore.core.reactive.ValueObserver
import neurochat.core.datastore.core.serialization.SerializationStrategy
import neurochat.core.datastore.core.validation.PreferencesValidator

/**
 * Reactive implementation of a user preferences data store with support for caching,
 * validation, and change notifications.
 *
 * This class provides coroutine-based, type-safe, and observable access to user preferences,
 * supporting both primitive and serializable types.
 *
 * Example usage:
 * ```kotlin
 * val dataStore = ReactiveUserPreferencesDataStore(
 *     settings = Settings(),
 *     dispatcher = Dispatchers.IO,
 *     typeHandlers = listOf(IntTypeHandler(), StringTypeHandler()),
 *     serializationStrategy = JsonSerializationStrategy(),
 *     validator = DefaultPreferencesValidator(),
 *     cacheManager = LruCacheManager(200),
 *     changeNotifier = DefaultChangeNotifier(),
 *     valueObserver = DefaultValueObserver(DefaultChangeNotifier())
 * )
 * ```
 *
 * @property settings The underlying settings storage implementation.
 * @property dispatcher The coroutine dispatcher for executing operations.
 * @property typeHandlers The list of type handlers for supported types.
 * @property serializationStrategy The strategy for serializing and deserializing values.
 * @property validator The validator for keys and values.
 * @property cacheManager The cache manager for in-memory caching.
 * @property changeNotifier The notifier for broadcasting change events.
 * @property valueObserver The observer for value changes.
 */
@Suppress("TooManyFunctions")
class ReactiveUserPreferencesDataStore(
    private val settings: Settings,
    private val dispatcher: CoroutineDispatcher,
    private val typeHandlers: List<TypeHandler<Any>>,
    private val serializationStrategy: SerializationStrategy,
    private val validator: PreferencesValidator,
    private val cacheManager: CacheManager<String, Any>,
    private val changeNotifier: ChangeNotifier,
    private val valueObserver: ValueObserver,
) : ReactiveDataStore {
    // Delegate to the base enhanced implementation
    private val enhancedDataStore =
        CachedPreferencesStore(
            settings,
            dispatcher,
            typeHandlers,
            serializationStrategy,
            validator,
            cacheManager,
        )

    /**
     * Stores a value associated with the specified key in the data store.
     *
     * @param key The key to associate with the value.
     * @param value The value to store.
     * @return [Result.success] if the operation succeeds, or [Result.failure] if an error occurs.
     *
     * Example usage:
     * ```kotlin
     * dataStore.putValue("theme", "dark")
     * ```
     */
    override suspend fun <T> putValue(
        key: String,
        value: T,
    ): Result<Unit> = withContext(dispatcher) {
        val oldValue =
            if (hasKey(key).getOrDefault(false)) {
                enhancedDataStore.getValue(key, value).getOrNull()
            } else {
                null
            }

        val result = enhancedDataStore.putValue(key, value)

        if (result.isSuccess) {
            val change =
                if (oldValue != null) {
                    DataStoreChangeEvent.ValueUpdated(key, oldValue, value)
                } else {
                    DataStoreChangeEvent.ValueAdded(key, value)
                }
            changeNotifier.notifyChange(change)
        }

        result
    }

    /**
     * Retrieves a value associated with the specified key from the data store.
     *
     * @param key The key to retrieve.
     * @param default The default value to return if the key does not exist.
     * @return [Result.success] with the value, or [Result.failure] if an error occurs.
     *
     * Example usage:
     * ```kotlin
     * dataStore.getValue("theme", "light")
     * ```
     */
    override suspend fun <T> getValue(
        key: String,
        default: T,
    ): Result<T> = enhancedDataStore.getValue(key, default)

    /**
     * Stores a serializable value using the provided serializer.
     *
     * @param key The key to associate with the value.
     * @param value The value to store.
     * @param serializer The serializer for the value type.
     * @return [Result.success] if the operation succeeds, or [Result.failure] if an error occurs.
     *
     * Example usage:
     * ```kotlin
     * dataStore.putSerializableValue("user", user, User.serializer())
     * ```
     */
    override suspend fun <T> putSerializableValue(
        key: String,
        value: T,
        serializer: KSerializer<T>,
    ): Result<Unit> = withContext(dispatcher) {
        val oldValue =
            if (hasKey(key).getOrDefault(false)) {
                enhancedDataStore.getSerializableValue(key, value, serializer).getOrNull()
            } else {
                null
            }

        val result = enhancedDataStore.putSerializableValue(key, value, serializer)

        if (result.isSuccess) {
            val change =
                if (oldValue != null) {
                    DataStoreChangeEvent.ValueUpdated(key, oldValue, value)
                } else {
                    DataStoreChangeEvent.ValueAdded(key, value)
                }
            changeNotifier.notifyChange(change)
        }

        result
    }

    /**
     * Retrieves a serializable value using the provided serializer.
     *
     * @param key The key to retrieve.
     * @param default The default value to return if the key does not exist.
     * @param serializer The serializer for the value type.
     * @return [Result.success] with the value, or [Result.failure] if an error occurs.
     *
     * Example usage:
     * ```kotlin
     * dataStore.getSerializableValue("user", defaultUser, User.serializer())
     * ```
     */
    override suspend fun <T> getSerializableValue(
        key: String,
        default: T,
        serializer: KSerializer<T>,
    ): Result<T> = enhancedDataStore.getSerializableValue(key, default, serializer)

    /**
     * Removes the value associated with the specified key from the data store.
     *
     * @param key The key to remove.
     * @return [Result.success] if the operation succeeds, or [Result.failure] if an error occurs.
     *
     * Example usage:
     * ```kotlin
     * dataStore.removeValue("theme")
     * ```
     */
    override suspend fun removeValue(key: String): Result<Unit> = withContext(dispatcher) {
        val oldValue =
            if (hasKey(key).getOrDefault(false)) {
                runCatching { settings.getString(key, "") }.getOrNull()
            } else {
                null
            }

        val result = enhancedDataStore.removeValue(key)

        if (result.isSuccess) {
            changeNotifier.notifyChange(
                DataStoreChangeEvent.ValueRemoved(key, oldValue),
            )
        }

        result
    }

    /**
     * Clears all stored preferences in the data store.
     *
     * @return [Result.success] if the operation succeeds, or [Result.failure] if an error occurs.
     *
     * Example usage:
     * ```kotlin
     * dataStore.clearAll()
     * ```
     */
    override suspend fun clearAll(): Result<Unit> = withContext(dispatcher) {
        val result = enhancedDataStore.clearAll()

        if (result.isSuccess) {
            changeNotifier.notifyChange(DataStoreChangeEvent.StoreCleared())
        }

        result
    }

    /**
     * Observes the value for the specified key as a flow, emitting updates as they occur.
     *
     * @param key The key to observe.
     * @param default The default value to emit if the key does not exist.
     * @return A [Flow] emitting the value for the key.
     *
     * Example usage:
     * ```kotlin
     * dataStore.observeValue("theme", "light").collect { value -> println(value) }
     * ```
     */
    override fun <T> observeValue(
        key: String,
        default: T,
    ): Flow<T> = valueObserver.createDistinctValueFlow(key, default) {
        enhancedDataStore.getValue(key, default)
    }

    /**
     * Observes a serializable value for the specified key as a flow.
     *
     * @param key The key to observe.
     * @param default The default value to emit if the key does not exist.
     * @param serializer The serializer for the value type.
     * @return A [Flow] emitting the value for the key.
     *
     * Example usage:
     * ```kotlin
     * dataStore
     *  .observeSerializableValue("user", defaultUser, User.serializer())
     *  .collect { user -> println(user) }
     * ```
     */
    override fun <T> observeSerializableValue(
        key: String,
        default: T,
        serializer: KSerializer<T>,
    ): Flow<T> = valueObserver.createDistinctValueFlow(key, default) {
        enhancedDataStore.getSerializableValue(key, default, serializer)
    }

    /**
     * Observes all keys in the data store as a flow, emitting updates as they occur.
     *
     * @return A [Flow] emitting the set of all keys.
     *
     * Example usage:
     * ```kotlin
     * dataStore.observeKeys().collect { keys -> println(keys) }
     * ```
     */
    override fun observeKeys(): Flow<Set<String>> = changeNotifier.observeChanges()
        .onStart { emit(DataStoreChangeEvent.ValueAdded("", null)) } // Trigger initial emission
        .map { settings.keys }

    /**
     * Observes the size of the data store as a flow, emitting updates as they occur.
     *
     * @return A [Flow] emitting the number of key-value pairs in the data store.
     *
     * Example usage:
     * ```kotlin
     * dataStore.observeSize().collect { size -> println(size) }
     * ```
     */
    override fun observeSize(): Flow<Int> = changeNotifier.observeChanges()
        .onStart { emit(DataStoreChangeEvent.ValueAdded("", null)) } // Trigger initial emission
        .map { getSize().getOrDefault(0) }
        .distinctUntilChanged()

    /**
     * Observes all change events in the data store as a flow.
     *
     * @return A [Flow] emitting [DataStoreChangeEvent] instances as changes occur.
     *
     * Example usage:
     * ```kotlin
     * dataStore.observeChanges().collect { event -> println(event) }
     * ```
     */
    override fun observeChanges(): Flow<DataStoreChangeEvent> = changeNotifier.observeChanges()

    /**
     * Checks if the specified key exists in the data store.
     *
     * @param key The key to check.
     * @return [Result.success] with true if the key exists, or [Result.failure] if an error occurs.
     *
     * Example usage:
     * ```kotlin
     * dataStore.hasKey("theme")
     * ```
     */
    override suspend fun hasKey(key: String): Result<Boolean> = enhancedDataStore.hasKey(key)

    /**
     * Retrieves all keys currently stored in the data store.
     *
     * @return [Result.success] with the set of keys, or [Result.failure] if an error occurs.
     *
     * Example usage:
     * ```kotlin
     * dataStore.getAllKeys()
     * ```
     */
    override suspend fun getAllKeys(): Result<Set<String>> = enhancedDataStore.getAllKeys()

    /**
     * Retrieves the total number of key-value pairs stored in the data store.
     *
     * @return [Result.success] with the count, or [Result.failure] if an error occurs.
     *
     * Example usage:
     * ```kotlin
     * dataStore.getSize()
     * ```
     */
    override suspend fun getSize(): Result<Int> = enhancedDataStore.getSize()

    /**
     * Invalidates the cache entry for the specified key.
     *
     * This forces the next retrieval for this key to read from the underlying data store.
     *
     * @param key The key whose cache entry should be invalidated.
     * @return [Result.success] if the operation succeeds, or [Result.failure] if an error occurs.
     *
     * Example usage:
     * ```kotlin
     * dataStore.invalidateCache("user_preferences")
     * ```
     */
    override suspend fun invalidateCache(key: String): Result<Unit> = enhancedDataStore.invalidateCache(key)

    /**
     * Invalidates all entries in the cache.
     *
     * This forces the next retrieval for any key to read from the underlying data store.
     *
     * @return [Result.success] if the operation succeeds, or [Result.failure] if an error occurs.
     *
     * Example usage:
     * ```kotlin
     * dataStore.invalidateAllCache()
     * ```
     */
    override suspend fun invalidateAllCache(): Result<Unit> = enhancedDataStore.invalidateAllCache()

    /**
     * Returns the current number of entries in the cache.
     *
     * @return The size of the cache.
     *
     * Example usage:
     * ```kotlin
     * val cacheSize = dataStore.getCacheSize()
     * println("Cache contains $cacheSize entries")
     * ```
     */
    override fun getCacheSize(): Int = enhancedDataStore.getCacheSize()
}

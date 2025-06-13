/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.datastore.core.factory

import com.russhwolf.settings.Settings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import neurochat.core.datastore.core.DefaultReactivePreferencesRepository
import neurochat.core.datastore.core.ReactivePreferencesRepository
import neurochat.core.datastore.core.cache.CacheManager
import neurochat.core.datastore.core.cache.LruCacheManager
import neurochat.core.datastore.core.contracts.ReactiveDataStore
import neurochat.core.datastore.core.handlers.BooleanTypeHandler
import neurochat.core.datastore.core.handlers.DoubleTypeHandler
import neurochat.core.datastore.core.handlers.FloatTypeHandler
import neurochat.core.datastore.core.handlers.IntTypeHandler
import neurochat.core.datastore.core.handlers.LongTypeHandler
import neurochat.core.datastore.core.handlers.StringTypeHandler
import neurochat.core.datastore.core.handlers.TypeHandler
import neurochat.core.datastore.core.reactive.ChangeNotifier
import neurochat.core.datastore.core.reactive.DefaultChangeNotifier
import neurochat.core.datastore.core.reactive.DefaultValueObserver
import neurochat.core.datastore.core.serialization.JsonSerializationStrategy
import neurochat.core.datastore.core.serialization.SerializationStrategy
import neurochat.core.datastore.core.store.ReactiveUserPreferencesDataStore
import neurochat.core.datastore.core.validation.DefaultPreferencesValidator
import neurochat.core.datastore.core.validation.PreferencesValidator

/**
 * Factory for constructing reactive data store repositories and data stores with customizable configuration.
 *
 * This class uses the builder pattern to allow flexible configuration of settings, dispatcher,
 * cache size, validator, serialization strategy, and change notifier.
 *
 * Example usage:
 * ```kotlin
 * // Simple usage with defaults
 * val repository = DataStoreFactory.create()
 *
 * // Custom configuration
 * val repository = DataStoreFactory()
 *     .cacheSize(500)
 *     .dispatcher(Dispatchers.IO)
 *     .settings(MyCustomSettings())
 *     .build()
 * ```
 */
class DataStoreFactory {
    private var settings: Settings? = null
    private var dispatcher: CoroutineDispatcher = Dispatchers.Default
    private var cacheSize: Int = 200
    private var validator: PreferencesValidator? = null
    private var serializationStrategy: SerializationStrategy? = null
    private var changeNotifier: ChangeNotifier? = null

    /**
     * Sets a custom [Settings] implementation for the data store.
     *
     * If not provided, the default [Settings] implementation will be used.
     *
     * @param settings The [Settings] instance to use.
     * @return This [DataStoreFactory] instance for chaining.
     */
    fun settings(settings: Settings) = apply {
        this.settings = settings
    }

    /**
     * Sets the coroutine [dispatcher] for data store operations.
     *
     * The default is [Dispatchers.Default].
     *
     * @param dispatcher The [CoroutineDispatcher] to use.
     * @return This [DataStoreFactory] instance for chaining.
     */
    fun dispatcher(dispatcher: CoroutineDispatcher) = apply {
        this.dispatcher = dispatcher
    }

    /**
     * Sets the cache size for the LRU cache.
     *
     * The default is 200 entries.
     *
     * @param size The maximum number of entries in the cache.
     * @return This [DataStoreFactory] instance for chaining.
     */
    fun cacheSize(size: Int) = apply {
        this.cacheSize = size
    }

    /**
     * Sets a custom [PreferencesValidator] for validating keys and values.
     *
     * If not provided, the default validator will be used.
     *
     * @param validator The [PreferencesValidator] to use.
     * @return This [DataStoreFactory] instance for chaining.
     */
    fun validator(validator: PreferencesValidator) = apply {
        this.validator = validator
    }

    /**
     * Sets a custom [SerializationStrategy] for serializing and deserializing values.
     *
     * If not provided, the default [JsonSerializationStrategy] will be used.
     *
     * @param strategy The [SerializationStrategy] to use.
     * @return This [DataStoreFactory] instance for chaining.
     */
    fun serializationStrategy(strategy: SerializationStrategy) = apply {
        this.serializationStrategy = strategy
    }

    /**
     * Sets a custom [ChangeNotifier] for broadcasting change events.
     *
     * If not provided, the default [DefaultChangeNotifier] will be used.
     *
     * @param notifier The [ChangeNotifier] to use.
     * @return This [DataStoreFactory] instance for chaining.
     */
    fun changeNotifier(notifier: ChangeNotifier) = apply {
        this.changeNotifier = notifier
    }

    /**
     * Builds and returns a [ReactivePreferencesRepository] with the current configuration.
     *
     * @return A fully configured [ReactivePreferencesRepository].
     *
     * Example usage:
     * ```kotlin
     * val repository = DataStoreFactory().build()
     * ```
     */
    fun build(): ReactivePreferencesRepository {
        val finalSettings = settings ?: Settings()
        val finalValidator = validator ?: DefaultPreferencesValidator()
        val finalSerializationStrategy = serializationStrategy ?: JsonSerializationStrategy()
        val finalChangeNotifier = changeNotifier ?: DefaultChangeNotifier()

        val cacheManager: CacheManager<String, Any> = LruCacheManager(maxSize = cacheSize)
        val valueObserver = DefaultValueObserver(finalChangeNotifier)

        @Suppress("UNCHECKED_CAST")
        val typeHandlers: List<TypeHandler<Any>> =
            listOf(
                IntTypeHandler(),
                StringTypeHandler(),
                BooleanTypeHandler(),
                LongTypeHandler(),
                FloatTypeHandler(),
                DoubleTypeHandler(),
            ) as List<TypeHandler<Any>>

        val reactiveDataStore =
            ReactiveUserPreferencesDataStore(
                settings = finalSettings,
                dispatcher = dispatcher,
                typeHandlers = typeHandlers,
                serializationStrategy = finalSerializationStrategy,
                validator = finalValidator,
                cacheManager = cacheManager,
                changeNotifier = finalChangeNotifier,
                valueObserver = valueObserver,
            )

        return DefaultReactivePreferencesRepository(reactiveDataStore)
    }

    /**
     * Builds and returns a [ReactiveDataStore] with the current configuration, without the repository wrapper.
     *
     * Use this if you need direct access to data store methods.
     *
     * @return A fully configured [ReactiveDataStore].
     *
     * Example usage:
     * ```kotlin
     * val dataStore = DataStoreFactory().buildDataStore()
     * ```
     */
    fun buildDataStore(): ReactiveDataStore {
        val finalSettings = settings ?: Settings()
        val finalValidator = validator ?: DefaultPreferencesValidator()
        val finalSerializationStrategy = serializationStrategy ?: JsonSerializationStrategy()
        val finalChangeNotifier = changeNotifier ?: DefaultChangeNotifier()

        val cacheManager: CacheManager<String, Any> = LruCacheManager(maxSize = cacheSize)
        val valueObserver = DefaultValueObserver(finalChangeNotifier)

        @Suppress("UNCHECKED_CAST")
        val typeHandlers: List<TypeHandler<Any>> =
            listOf(
                IntTypeHandler(),
                StringTypeHandler(),
                BooleanTypeHandler(),
                LongTypeHandler(),
                FloatTypeHandler(),
                DoubleTypeHandler(),
            ) as List<TypeHandler<Any>>

        return ReactiveUserPreferencesDataStore(
            settings = finalSettings,
            dispatcher = dispatcher,
            typeHandlers = typeHandlers,
            serializationStrategy = finalSerializationStrategy,
            validator = finalValidator,
            cacheManager = cacheManager,
            changeNotifier = finalChangeNotifier,
            valueObserver = valueObserver,
        )
    }

    companion object {
        /**
         * Creates a [ReactivePreferencesRepository] with default configuration.
         *
         * This is the simplest way to obtain a working data store repository.
         *
         * @return A [ReactivePreferencesRepository] with default settings.
         *
         * Example usage:
         * ```kotlin
         * val repository = DataStoreFactory.create()
         * ```
         */
        fun create(): ReactivePreferencesRepository = DataStoreFactory().build()

        /**
         * Creates a [ReactivePreferencesRepository] with a custom [Settings] instance.
         *
         * Useful for providing platform-specific settings.
         *
         * @param settings The [Settings] instance to use.
         * @return A [ReactivePreferencesRepository] with the specified settings.
         *
         * Example usage:
         * ```kotlin
         * val repository = DataStoreFactory.create(customSettings)
         * ```
         */
        fun create(settings: Settings): ReactivePreferencesRepository = DataStoreFactory()
            .settings(settings)
            .build()

        /**
         * Creates a [ReactivePreferencesRepository] with a custom [Settings] instance and [CoroutineDispatcher].
         *
         * Useful for platform-specific configurations (e.g., Android/iOS).
         *
         * @param settings The [Settings] instance to use.
         * @param dispatcher The [CoroutineDispatcher] to use.
         * @return A [ReactivePreferencesRepository] with the specified settings and dispatcher.
         *
         * Example usage:
         * ```kotlin
         * val repository = DataStoreFactory.create(customSettings, Dispatchers.IO)
         * ```
         */
        fun create(
            settings: Settings,
            dispatcher: CoroutineDispatcher,
        ): ReactivePreferencesRepository = DataStoreFactory()
            .settings(settings)
            .dispatcher(dispatcher)
            .build()
    }
}

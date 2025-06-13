/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.datastore.core.contracts

import kotlinx.datetime.Clock.System

/**
 * Represents a change event that occurs in the data store.
 *
 * This sealed class defines different types of events, such as value additions, updates,
 * removals, and the clearing of the entire store.
 *
 * Example usage:
 * ```kotlin
 * dataStore.observeChanges().collect { event ->
 *     when (event) {
 *         is DataStoreChangeEvent.ValueAdded -> println("Value added for key ${event.key}")
 *         is DataStoreChangeEvent.ValueUpdated -> println("Value for key ${event.key} updated")
 *         is DataStoreChangeEvent.ValueRemoved -> println("Value for key ${event.key} removed")
 *         is DataStoreChangeEvent.StoreCleared -> println("Data store cleared")
 *     }
 * }
 * ```
 */
sealed class DataStoreChangeEvent {
    /**
     * The key associated with the change event. For [StoreCleared], this is typically "*".
     */
    abstract val key: String

    /**
     * The timestamp (in milliseconds since the epoch) when the event occurred.
     */
    abstract val timestamp: Long

    data class ValueAdded(
        /**
         * Represents the addition of a new value to the data store.
         *
         * @property key The key of the added value.
         * @property value The value that was added.
         * @property timestamp The timestamp of the event.
         */
        override val key: String,
        val value: Any?,
        override val timestamp: Long = System.now().toEpochMilliseconds(),
    ) : DataStoreChangeEvent()

    data class ValueUpdated(
        /**
         * Represents the update of an existing value in the data store.
         *
         * @property key The key of the updated value.
         * @property oldValue The previous value before the update.
         * @property newValue The new value after the update.
         * @property timestamp The timestamp of the event.
         */
        override val key: String,
        val oldValue: Any?,
        val newValue: Any?,
        override val timestamp: Long = System.now().toEpochMilliseconds(),
    ) : DataStoreChangeEvent()

    data class ValueRemoved(
        /**
         * Represents the removal of a value from the data store.
         *
         * @property key The key of the removed value.
         * @property oldValue The value that was removed.
         * @property timestamp The timestamp of the event.
         */
        override val key: String,
        val oldValue: Any?,
        override val timestamp: Long = System.now().toEpochMilliseconds(),
    ) : DataStoreChangeEvent()

    data class StoreCleared(
        /**
         * Represents the clearing of the entire data store.
         *
         * @property key The key is typically "*" for store cleared events.
         * @property timestamp The timestamp of the event.
         */
        override val key: String = "*",
        override val timestamp: Long = System.now().toEpochMilliseconds(),
    ) : DataStoreChangeEvent()
}

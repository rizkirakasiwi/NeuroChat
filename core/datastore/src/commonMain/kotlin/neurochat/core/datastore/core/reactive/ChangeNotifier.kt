/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.datastore.core.reactive

import kotlinx.coroutines.flow.Flow
import neurochat.core.datastore.core.contracts.DataStoreChangeEvent

/**
 * Interface for notifying and observing changes in the data store.
 *
 * Implementations of this interface allow for broadcasting change events
 * and subscribing to them, enabling reactive updates throughout the application.
 *
 * Example usage:
 * ```kotlin
 * val notifier: ChangeNotifier = DefaultChangeNotifier()
 * notifier.notifyChange(DataStoreChangeEvent.ValueAdded("key", "value"))
 * notifier.observeChanges().collect { event -> println(event) }
 * ```
 */
interface ChangeNotifier {
    /**
     * Notifies listeners of a change event in the data store.
     *
     * @param change The event describing the change.
     */
    fun notifyChange(change: DataStoreChangeEvent)

    /**
     * Observes all change events in the data store.
     *
     * @return A [Flow] emitting [DataStoreChangeEvent] instances as changes occur.
     */
    fun observeChanges(): Flow<DataStoreChangeEvent>

    /**
     * Observes change events for a specific key in the data store.
     *
     * @param key The key to observe for changes.
     * @return A [Flow] emitting [DataStoreChangeEvent] instances related to the specified key.
     */
    fun observeKeyChanges(key: String): Flow<DataStoreChangeEvent>

    /**
     * Clears all listeners and resources associated with this notifier.
     *
     * This should be called to release resources when the notifier is no longer needed.
     */
    fun clear()
}

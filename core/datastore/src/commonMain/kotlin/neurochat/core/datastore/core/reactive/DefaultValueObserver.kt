/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.datastore.core.reactive

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import neurochat.core.datastore.core.contracts.DataStoreChangeEvent

/**
 * Default implementation of [ValueObserver] for observing preference value changes.
 *
 * This observer uses a [ChangeNotifier] to emit value changes as flows, supporting both initial and distinct emissions.
 *
 * Example usage:
 * ```kotlin
 * val observer = DefaultValueObserver(changeNotifier)
 * observer.createValueFlow("theme", "light") { getTheme() }
 * ```
 *
 * @property changeNotifier The notifier used to observe key changes.
 */
class DefaultValueObserver(
    private val changeNotifier: ChangeNotifier,
) : ValueObserver {
    /**
     * Creates a flow that emits the value for the specified key, starting with an initial emission.
     *
     * @param key The preference key to observe.
     * @param default The default value to emit if retrieval fails.
     * @param getter A suspend function to retrieve the value.
     * @return A [Flow] emitting the value for the key.
     *
     * Example usage:
     * ```kotlin
     * observer.createValueFlow("theme", "light") { getTheme() }
     * ```
     */
    override fun <T> createValueFlow(
        key: String,
        default: T,
        getter: suspend () -> Result<T>,
    ): Flow<T> {
        return changeNotifier.observeKeyChanges(key)
            // Trigger initial emission
            .onStart { emit(DataStoreChangeEvent.ValueAdded(key, null)) }
            .map { getter().getOrElse { default } }
    }

    /**
     * Creates a flow that emits distinct values for the specified key, suppressing duplicates.
     *
     * @param key The preference key to observe.
     * @param default The default value to emit if retrieval fails.
     * @param getter A suspend function to retrieve the value.
     * @return A [Flow] emitting only distinct values for the key.
     *
     * Example usage:
     * ```kotlin
     * observer.createDistinctValueFlow("theme", "light") { getTheme() }
     * ```
     */
    override fun <T> createDistinctValueFlow(
        key: String,
        default: T,
        getter: suspend () -> Result<T>,
    ): Flow<T> {
        return createValueFlow(key, default, getter).distinctUntilChanged()
    }
}

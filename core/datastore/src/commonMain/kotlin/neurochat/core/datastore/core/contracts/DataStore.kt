/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.datastore.core.contracts

/**
 * Base interface defining fundamental data storage operations.
 *
 * Implementations provide methods for checking key existence, removing values,
 * clearing the store, and retrieving keys and size.
 *
 * Example usage:
 * ```kotlin
 * val dataStore: DataStore = ...
 * dataStore.hasKey("config")
 * dataStore.removeValue("temp_data")
 * dataStore.getAllKeys()
 * ```
 */
interface DataStore {
    /**
     * Checks if the specified key exists in the data store.
     *
     * @param key The key to check.
     * @return [Result.success] with `true` if the key exists, `false` otherwise,
     * or [Result.failure] if an error occurs.
     */
    suspend fun hasKey(key: String): Result<Boolean>

    /**
     * Removes the value associated with the specified key from the data store.
     *
     * @param key The key to remove.
     * @return [Result.success] if the operation succeeds, or [Result.failure] if an error occurs.
     */
    suspend fun removeValue(key: String): Result<Unit>

    /**
     * Clears all stored data from the data store.
     *
     * @return [Result.success] if the operation succeeds, or [Result.failure] if an error occurs.
     */
    suspend fun clearAll(): Result<Unit>

    /**
     * Retrieves a set of all keys currently stored in the data store.
     *
     * @return [Result.success] with the set of keys, or [Result.failure] if an error occurs.
     */
    suspend fun getAllKeys(): Result<Set<String>>

    /**
     * Retrieves the total number of key-value pairs stored in the data store.
     *
     * @return [Result.success] with the count, or [Result.failure] if an error occurs.
     */
    suspend fun getSize(): Result<Int>
}

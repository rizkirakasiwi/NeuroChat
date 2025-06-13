/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.datastore.core.cache

/**
 * Interface for managing in-memory caching of key-value pairs in the data store.
 *
 * Implementations of this interface provide methods for storing, retrieving, removing,
 * and clearing cached entries, as well as checking cache size and key existence.
 *
 * Example usage:
 * ```kotlin
 * val cache: CacheManager<String, Any> = LruCacheManager(100)
 * cache.put("theme", "dark")
 * val value = cache.get("theme")
 * cache.remove("theme")
 * cache.clear()
 * val size = cache.size()
 * val exists = cache.containsKey("theme")
 * ```
 *
 * @param K The type of the cache key.
 * @param V The type of the cached value.
 */
interface CacheManager<K, V> {
    /**
     * Stores a value in the cache associated with the specified key.
     *
     * @param key The key to associate with the value.
     * @param value The value to store.
     */
    fun put(
        key: K,
        value: V,
    )

    /**
     * Retrieves a value from the cache associated with the specified key.
     *
     * @param key The key to retrieve.
     * @return The cached value, or `null` if not present.
     */
    fun get(key: K): V?

    /**
     * Removes the value associated with the specified key from the cache.
     *
     * @param key The key to remove.
     * @return The removed value, or `null` if not present.
     */
    fun remove(key: K): V?

    /**
     * Clears all entries from the cache.
     */
    fun clear()

    /**
     * Returns the number of entries currently stored in the cache.
     *
     * @return The cache size.
     */
    fun size(): Int

    /**
     * Checks whether the cache contains the specified key.
     *
     * @param key The key to check.
     * @return `true` if the cache contains the key, `false` otherwise.
     */
    fun containsKey(key: K): Boolean
}

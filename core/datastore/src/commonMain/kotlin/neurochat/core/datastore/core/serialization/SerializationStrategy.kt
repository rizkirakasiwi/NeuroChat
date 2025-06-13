/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.datastore.core.serialization

import kotlinx.serialization.KSerializer

/**
 * Strategy for handling serialization operations.
 * Follows Single Responsibility Principle.
 */
interface SerializationStrategy {
    suspend fun <T> serialize(
        value: T,
        serializer: KSerializer<T>,
    ): Result<String>

    suspend fun <T> deserialize(
        data: String,
        serializer: KSerializer<T>,
    ): Result<T>
}

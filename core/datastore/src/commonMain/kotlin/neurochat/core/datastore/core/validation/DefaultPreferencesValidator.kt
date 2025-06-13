/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.datastore.core.validation

import neurochat.core.datastore.core.exceptions.InvalidKeyException

/**
 * Default implementation of [PreferencesValidator] for validating keys and values in the data store.
 *
 * This implementation enforces constraints such as non-blank keys, maximum key length, and value size limits.
 *
 * Example usage:
 * ```kotlin
 * val validator = DefaultPreferencesValidator()
 * validator.validateKey("theme")
 * validator.validateValue("dark")
 * ```
 */
class DefaultPreferencesValidator : PreferencesValidator {
    companion object {
        private const val MAX_KEY_LENGTH = 255
        private const val MAX_VALUE_LENGTH = 10000
    }

    /**
     * {@inheritDoc}
     */
    override fun validateKey(key: String): Result<Unit> {
        return when {
            key.isBlank() ->
                Result.failure(
                    InvalidKeyException("Key cannot be blank"),
                )

            key.length > MAX_KEY_LENGTH ->
                Result.failure(
                    InvalidKeyException("Key length cannot exceed 255 characters: '$key'"),
                )

            key.contains('\u0000') ->
                Result.failure(
                    InvalidKeyException("Key cannot contain null characters: '$key'"),
                )

            else -> Result.success(Unit)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun <T> validateValue(value: T): Result<Unit> {
        return when (value) {
            null ->
                Result.failure(
                    IllegalArgumentException("Value cannot be null"),
                )

            is String -> {
                if (value.length > MAX_VALUE_LENGTH) {
                    Result.failure(
                        IllegalArgumentException("String value too large: ${value.length} characters"),
                    )
                } else {
                    Result.success(Unit)
                }
            }

            else -> Result.success(Unit)
        }
    }
}

/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.datastore.core.validation

/**
 * Interface for validating keys and values used in the data store.
 *
 * Implementations of this interface provide validation logic to ensure that keys and values meet
 * required constraints before being stored or retrieved.
 *
 * Example usage:
 * ```kotlin
 * val validator: PreferencesValidator = DefaultPreferencesValidator()
 * validator.validateKey("theme")
 * validator.validateValue("dark")
 * ```
 */
interface PreferencesValidator {
    /**
     * Validates the provided key for use in the data store.
     *
     * @param key The key to validate.
     * @return [Result.success] if the key is valid, or [Result.failure] with an exception if invalid.
     */
    fun validateKey(key: String): Result<Unit>

    /**
     * Validates the provided value for use in the data store.
     *
     * @param value The value to validate.
     * @return [Result.success] if the value is valid, or [Result.failure] with an exception if invalid.
     */
    fun <T> validateValue(value: T): Result<Unit>
}

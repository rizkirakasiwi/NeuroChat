/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.datastore

import kotlinx.coroutines.flow.Flow
import neurochat.core.model.DarkThemeConfig
import neurochat.core.model.ThemeBrand
import neurochat.core.model.UserData

/**
 * Repository interface for managing user preferences with reactive capabilities.
 *
 * This interface provides reactive access to user preferences including theme settings,
 * dark mode configuration, and dynamic color preferences.
 */
@Suppress("TooManyFunctions")
interface UserPreferencesRepository {
    /**
     * Reactive stream of current user data combining all preferences.
     * Emits whenever any preference changes.
     */
    val userData: Flow<UserData>

    // Theme Brand Operations
    suspend fun setThemeBrand(themeBrand: ThemeBrand): Result<Unit>

    suspend fun getThemeBrand(): Result<ThemeBrand>

    fun observeThemeBrand(): Flow<ThemeBrand>

    // Dark Theme Configuration Operations
    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig): Result<Unit>

    suspend fun getDarkThemeConfig(): Result<DarkThemeConfig>

    fun observeDarkThemeConfig(): Flow<DarkThemeConfig>

    // Dynamic Color Preference Operations
    suspend fun setDynamicColorPreference(useDynamicColor: Boolean): Result<Unit>

    suspend fun getDynamicColorPreference(): Result<Boolean>

    fun observeDynamicColorPreference(): Flow<Boolean>

    // Batch Operations
    suspend fun resetToDefaults(): Result<Unit>

    suspend fun exportPreferences(): Result<UserData>

    suspend fun importPreferences(userData: UserData): Result<Unit>
}

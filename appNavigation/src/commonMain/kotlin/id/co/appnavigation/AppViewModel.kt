/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package id.co.appnavigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import neurochat.core.datastore.UserPreferencesRepository
import neurochat.core.model.DarkThemeConfig
import neurochat.core.model.ThemeBrand
import neurochat.core.model.UserData

class AppViewModel(
    settingsRepository: UserPreferencesRepository,
) : ViewModel() {
    val uiState: StateFlow<AppUiState> =
        settingsRepository.userData
            .map { userDate ->
                AppUiState.Success(userDate)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = AppUiState.Loading,
            )
}

sealed interface AppUiState {
    data object Loading : AppUiState

    data class Success(val userData: UserData) : AppUiState {
        override val shouldDisplayDynamicTheming = userData.useDynamicColor
        override val shouldUseAndroidTheme =
            when (userData.themeBrand) {
                ThemeBrand.DEFAULT -> false
                ThemeBrand.ANDROID -> true
            }

        override fun shouldUseDarkTheme(isSystemInDarkTheme: Boolean): Boolean =
            when (userData.darkThemeConfig) {
                DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme
                DarkThemeConfig.LIGHT -> false
                DarkThemeConfig.DARK -> true
            }
    }

    val shouldDisplayDynamicTheming: Boolean get() = true
    val shouldUseAndroidTheme: Boolean get() = false

    fun shouldUseDarkTheme(isSystemInDarkTheme: Boolean) = isSystemInDarkTheme
}

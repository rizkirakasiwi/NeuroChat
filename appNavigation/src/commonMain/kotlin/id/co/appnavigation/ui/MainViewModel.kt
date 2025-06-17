/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package id.co.appnavigation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.appnavigation.ui.data.ChatHistory
import id.co.appnavigation.ui.data.History
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import neurochat.core.data.utils.NetworkMonitor

sealed interface MainState {
    data object Idle : MainState
    data object Loading : MainState
    data object Error : MainState
    data class HistoryLoaded(val histories: ImmutableList<ChatHistory>) : MainState {
        companion object {
            val dummyData = persistentListOf(
                ChatHistory(
                    id = "1",
                    title = "Kemarin",
                    history = persistentListOf(
                        History(
                            id = "1",
                            title = "I Build some Rest API",
                            timeInMillis = Clock.System.now().toEpochMilliseconds(),
                        ),
                    ),
                ),
                ChatHistory(
                    id = "2",
                    title = "7 Hari",
                    history = persistentListOf(
                        History(
                            id = "2",
                            title = "Using dispatchers.IO in android",
                            timeInMillis = Clock.System.now().toEpochMilliseconds(),
                        ),
                        History(
                            id = "3",
                            title = "Update Component Name",
                            timeInMillis = Clock.System.now().toEpochMilliseconds(),
                        ),
                    ),
                ),
                ChatHistory(
                    id = "3",
                    title = "30 Hari",
                    history = persistentListOf(
                        History(
                            id = "4",
                            title = "AI Role in Business operation",
                            timeInMillis = Clock.System.now().toEpochMilliseconds(),
                        ),
                        History(
                            id = "5",
                            title = "Flutter monorepo project stucture",
                            timeInMillis = Clock.System.now().toEpochMilliseconds(),
                        ),
                    ),
                ),
            )
        }
    }
}

class MainViewModel(
    networkMonitor: NetworkMonitor,
) : ViewModel() {
    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state = _state.asStateFlow()

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
        )

    private var job: Job? = null

    fun loadChatHistories() {
        job?.cancel()
        _state.tryEmit(MainState.Loading)
        job = viewModelScope.launch {
            _state.tryEmit(MainState.HistoryLoaded(MainState.HistoryLoaded.dummyData))
        }
    }
}

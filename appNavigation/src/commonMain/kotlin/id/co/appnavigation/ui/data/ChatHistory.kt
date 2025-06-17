/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package id.co.appnavigation.ui.data

import kotlinx.collections.immutable.ImmutableList

data class ChatHistory(
    val id: String,
    val title: String,
    val history: ImmutableList<History>,
)

data class History(
    val id: String,
    val title: String,
    val timeInMillis: Long,
)

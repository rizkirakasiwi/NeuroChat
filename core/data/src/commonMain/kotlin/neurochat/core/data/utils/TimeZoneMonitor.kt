/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.data.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.TimeZone

/**
 * Utility for reporting current timezone the device has set.
 * It always emits at least once with default setting and then for each TZ change.
 */

interface TimeZoneMonitor {
    val currentTimeZone: Flow<TimeZone>
}

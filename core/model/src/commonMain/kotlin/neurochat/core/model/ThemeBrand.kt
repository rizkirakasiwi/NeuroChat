/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.model

enum class ThemeBrand(val brandName: String) {
    DEFAULT("Default"),
    ANDROID("Android"),
    ;

    companion object {
        fun fromString(value: String): ThemeBrand = entries.find { it.brandName.equals(value, ignoreCase = true) } ?: DEFAULT
    }
}

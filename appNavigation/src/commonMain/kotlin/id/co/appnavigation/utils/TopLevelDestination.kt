/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package id.co.appnavigation.utils

import androidx.compose.ui.graphics.vector.ImageVector
import app.navigation.generated.resources.Res
import app.navigation.generated.resources.app_name
import app.navigation.generated.resources.home
import neurochat.core.designsystem.icon.AppIcons
import org.jetbrains.compose.resources.StringResource

/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). Navigation from one screen to the
 * next within a single destination will be handled directly in composables.
 */
enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconText: StringResource,
    val titleText: StringResource,
) {
    HOME(
        selectedIcon = AppIcons.Home,
        unselectedIcon = AppIcons.HomeBoarder,
        iconText = Res.string.home,
        titleText = Res.string.app_name,
    ),
}

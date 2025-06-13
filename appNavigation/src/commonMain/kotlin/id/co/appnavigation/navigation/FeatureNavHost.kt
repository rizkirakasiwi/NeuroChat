/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package id.co.appnavigation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
internal fun FeatureNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        route = NavGraphRoute.MAIN_GRAPH,
        startDestination = "HOME_ROUTE", // feature/home
        navController = navController,
        modifier = modifier,
    ) {
        // navigation here
        composable("HOME_ROUTE") {
            Scaffold {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text("Home", modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

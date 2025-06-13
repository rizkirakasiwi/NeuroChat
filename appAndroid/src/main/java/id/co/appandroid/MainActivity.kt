/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package id.co.appandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import id.co.appshared.SharedApp
import neurochat.core.ui.ShareUtils

/**
 * Main activity class.
 * This class is used to set the content view of the activity.
 *
 * @constructor Create empty Main activity
 * @see ComponentActivity
 */
class MainActivity : ComponentActivity() {
    /**
     * Called when the activity is starting.
     * This is where most initialization should go: calling [setContentView(int)] to inflate the activity's UI,
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()

        ShareUtils.setActivityProvider { return@setActivityProvider this }

        /**
         * Set the content view of the activity.
         * @see setContent
         */
        setContent {
            SharedApp()
        }
    }
}

/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "neurochat.core.platform"

    buildFeatures {
        buildConfig = true
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.runtime)
            implementation(libs.calf.permissions)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.ktx)
            implementation(libs.androidx.activity.compose)

            implementation(libs.androidx.metrics)
            implementation(libs.androidx.browser)
            implementation(libs.androidx.compose.runtime)

            implementation(compose.material3)
        }
    }
}

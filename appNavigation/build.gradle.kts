/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.convention.cmp.feature)
    alias(libs.plugins.convention.kmp.koin)
}

android {
    namespace = "id.co.appnavigation"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Core Modules
            implementation(projects.core.data)
            implementation(projects.core.model)
            implementation(projects.core.common)
            implementation(projects.core.datastore)
//
//            implementation(projects.feature.home)
//            implementation(projects.feature.profile)
//            implementation(projects.feature.settings)

            // put your multiplatform dependencies here
            implementation(compose.material3)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.components.resources)
            implementation(libs.window.size)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
        }
    }
}

compose.resources {
    publicResClass = true
    generateResClass = always
    packageOfResClass = "app.navigation.generated.resources"
}

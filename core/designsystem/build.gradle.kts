/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.jetbrainsCompose)
}

android {
    namespace = "neurochat.core.designsystem"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}
kotlin {

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(projects.core.model)
        }
        androidInstrumentedTest.dependencies {
            implementation(libs.androidx.compose.ui.test)
        }
        androidUnitTest.dependencies {
            implementation(libs.androidx.compose.ui.test)
        }
        commonMain.dependencies {
            implementation(libs.coil.kt.compose)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.uiUtil)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            api(libs.back.handler)
            api(libs.window.size)
        }
    }
}

compose.resources {
    publicResClass = true
    generateResClass = always
    packageOfResClass = "neurochat.core.designsystem.generated.resources"
}

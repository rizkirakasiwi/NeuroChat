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
}

android {
    namespace = "neurochat.core.ui"
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            api(libs.androidx.metrics)
            implementation(libs.androidx.browser)
            implementation(libs.androidx.compose.runtime)
        }

        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.material3)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.components.resources)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.jb.composeViewmodel)
            implementation(libs.jb.lifecycle.compose)
            implementation(libs.jb.lifecycleViewmodel)
            implementation(libs.jb.composeNavigation)
            implementation(libs.jb.lifecycleViewmodelSavedState)

            implementation(libs.coil.kt)
            implementation(libs.coil.kt.compose)

            implementation(libs.filekit.core)
            implementation(libs.filekit.compose)
        }
        androidInstrumentedTest.dependencies {
            implementation(libs.bundles.androidx.compose.ui.test)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.common)
            implementation(compose.desktop.currentOs)
        }
    }
}

compose.resources {
    publicResClass = true
    generateResClass = always
    packageOfResClass = "neurochat.core.ui.generated.resources"
}

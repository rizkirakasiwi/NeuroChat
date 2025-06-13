/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.convention.cmp.feature)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            optimized = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Navigation Modules
            implementation(projects.appNavigation)
            implementation(projects.core.data)
            implementation(compose.components.resources)
            implementation(projects.core.platform)
            implementation(projects.core.ui)

            implementation(libs.coil.kt.compose)
        }

        desktopMain.dependencies {
            // Desktop specific dependencies
            implementation(compose.desktop.currentOs)
            implementation(compose.desktop.common)
        }
    }
}

android {
    namespace = "id.co.appshared"
}

compose.resources {
    publicResClass = true
    generateResClass = always
    packageOfResClass = "app.shared.generated.resources"
}

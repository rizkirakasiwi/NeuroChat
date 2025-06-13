/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
plugins {
    alias(libs.plugins.convention.kmp.library)
    id("kotlinx-serialization")
}

android {
    namespace = "neurochat.core.datastore"
    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.serialization)
            implementation(libs.multiplatform.settings.coroutines)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(projects.core.model)
            implementation(projects.core.common)
        }
        commonTest.dependencies {
            implementation(libs.multiplatform.settings.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.turbine)
        }
    }
}

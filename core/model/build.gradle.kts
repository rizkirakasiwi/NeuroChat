/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.kotlin.parcelize)
    id("kotlinx-serialization")
}

android {
    namespace = "neurochat.core.model"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
//            implementation(projects.core.common)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}

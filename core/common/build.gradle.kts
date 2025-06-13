/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "neurochat.core.common"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            api(libs.kermit.logging)
            api(libs.squareup.okio)
            api(libs.jb.kotlin.stdlib)
            api(libs.kotlinx.datetime)
        }

        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.android)
        }
        commonTest.dependencies {
            implementation(libs.kotlinx.coroutines.test)
        }
        iosMain.dependencies {
            api(libs.kermit.simple)
        }
        desktopMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.kotlin.reflect)
        }
        jsMain.dependencies {
            api(libs.jb.kotlin.stdlib.js)
            api(libs.jb.kotlin.dom)
        }
    }
}

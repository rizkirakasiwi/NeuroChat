/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
import com.neuro.chat.convention.AppBuildType
import com.neuro.chat.convention.dynamicVersion

plugins {
    alias(libs.plugins.convention.application.android)
    alias(libs.plugins.convention.application.compose)
    alias(libs.plugins.convention.application.flavors)
    id("com.google.devtools.ksp")
    alias(libs.plugins.kotlin.android)
}

val packageNameSpace: String = libs.versions.androidPackageNamespace.get()

android {
    namespace = "id.co.appandroid"
    defaultConfig {
        applicationId = packageNameSpace
        versionName = System.getenv("VERSION") ?: project.dynamicVersion
        versionCode = System.getenv("VERSION_CODE")?.toIntOrNull() ?: 1
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

//    signingConfigs {
//        create("release") {
//            storeFile = file(System.getenv("KEYSTORE_PATH") ?: "../keystores/release_keystore.keystore")
//            storePassword = System.getenv("KEYSTORE_PASSWORD") ?: "Wizard@123"
//            keyAlias = System.getenv("KEYSTORE_ALIAS") ?: "kmp-project-template"
//            keyPassword = System.getenv("KEYSTORE_ALIAS_PASSWORD") ?: "Wizard@123"
//            enableV1Signing = true
//            enableV2Signing = true
//        }
//    }

    buildTypes {
        debug {
            applicationIdSuffix = AppBuildType.DEBUG.applicationIdSuffix
        }

        // Disabling proguard for now until
        // https://github.com/openMF/mobile-wallet/issues/1815 this issue is resolved
        release {
            isMinifyEnabled = false
            applicationIdSuffix = AppBuildType.RELEASE.applicationIdSuffix
            isShrinkResources = false
            isDebuggable = false
            isJniDebuggable = false
//            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        buildConfig = true
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(projects.appShared)
    implementation(projects.core.ui)
    implementation(projects.core.platform)
    implementation(projects.core.ui)

    // Compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.activity.ktx)

    implementation(libs.androidx.tracing.ktx)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)

    implementation(libs.coil.kt)

    runtimeOnly(libs.androidx.compose.runtime)
    debugImplementation(libs.androidx.compose.ui.tooling)

    testImplementation(libs.junit)
    testImplementation(libs.androidx.compose.ui.test)

    androidTestImplementation(libs.androidx.compose.ui.test)
    androidTestImplementation(libs.androidx.testExt.junit)

    testImplementation(libs.koin.test.junit4)
}

dependencyGuard {
    configuration("prodReleaseRuntimeClasspath") {
        modules = true
        tree = true
    }
}

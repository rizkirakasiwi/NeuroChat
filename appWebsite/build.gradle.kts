import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    js(IR) {
        outputModuleName = "app-web"
        browser {
            commonWebpackConfig {
                outputFileName = "app-web.js"
            }
        }
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName = "app-wasm"
        browser {
            commonWebpackConfig {
                outputFileName = "app-wasm.js"
            }
        }
        binaries.executable()
    }

    applyDefaultHierarchyTemplate()

    sourceSets {
        val jsWasmMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                implementation(projects.appShared)
                implementation(projects.core.common)
                implementation(projects.core.data)
                implementation(projects.core.model)
                implementation(projects.core.datastore)

                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.components.resources)

                implementation(libs.multiplatform.settings)
                implementation(libs.multiplatform.settings.serialization)
                implementation(libs.multiplatform.settings.coroutines)

                implementation(libs.koin.core)
            }
        }

        jsMain.get().dependsOn(jsWasmMain)
        wasmJsMain.get().dependsOn(jsWasmMain)
    }
}

compose.resources {
    publicResClass = true
    generateResClass = always
}
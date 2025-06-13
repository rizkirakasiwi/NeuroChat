package com.neuro.chat.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        dependencies {
            val bom = library("androidx-compose-bom")
            implementation(platform(bom))
            androidTestImplementation(platform(bom))
            implementation(library("androidx-compose-ui-tooling-preview"))
            debugImplementation(library("androidx-compose-ui-tooling"))
        }

        testOptions {
            unitTests {
                // For Robolectric
                isIncludeAndroidResources = true

                isReturnDefaultValues = true

                all {
                    it.systemProperties["robolectric.pixelCopyRenderMode"] = "hardware"
                }
            }
        }
        
    }

    extensions.configure<ComposeCompilerGradlePluginExtension> {
        fun Provider<String>.onlyIfTrue() = flatMap { provider { it.takeIf(String::toBoolean) } }
        fun Provider<*>.relativeToRootProject(dir: String) = flatMap {
            rootProject.layout.buildDirectory.dir(projectDir.toRelativeString(rootDir))
        }.map { it.dir(dir) }

        project.providers.gradleProperty("enableComposeCompilerMetrics").onlyIfTrue()
            .relativeToRootProject("compose-metrics")
            .let(metricsDestination::set)

        project.providers.gradleProperty("enableComposeCompilerReports").onlyIfTrue()
            .relativeToRootProject("compose-reports")
            .let(reportsDestination::set)

        stabilityConfigurationFiles
            .add(isolated.rootProject.projectDirectory.file("compose_compiler_config.conf"))
    }
}
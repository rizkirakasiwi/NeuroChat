package com.neuro.chat.convention

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.named

/**
 * Configures the Detekt plugin with the [extension] configuration.
 * This includes setting the JVM target to 17 and enabling all reports.
 * Additionally, it adds the `detekt-formatting` and `twitter-detekt-compose` plugins.
 * @see DetektExtension
 * @see Detekt
 */
internal fun Project.configureDetekt(extension: DetektExtension) = extension.apply {
    tasks.named<Detekt>("detekt") {
        mustRunAfter(":appAndroid:dependencyGuard")
        jvmTarget = "21"
        source(files(rootDir))
        include("**/*.kt")
        exclude("**/*.kts")
        exclude("**/resources/**")
        exclude("**/build/**")
        exclude("**/generated/**")
        exclude("**/build-logic/**")
        exclude("**/spotless/**")
        reports {
            xml.required.set(true)
            html.required.set(true)
            txt.required.set(true)
            sarif.required.set(true)
            md.required.set(true)
        }
    }
    dependencies {
        detektPlugins(library("detekt-formatting"))
        detektPlugins(library("detekt-compose-rules"))
    }
}
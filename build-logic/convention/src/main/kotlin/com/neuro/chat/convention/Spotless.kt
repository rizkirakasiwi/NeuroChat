package com.neuro.chat.convention

import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Project

/**
 * Ktlint cli version
 * */
const val ktlintVersion = "1.6.0"

/**
 * Configures the Spotless plugin with the [extension] configuration.
 * This includes setting up the `ktlint` formatter and the license header.
 * @see SpotlessExtension
 */
internal fun Project.configureSpotless(extension: SpotlessExtension) = extension.apply {
    kotlin {
        target("**/*.kt")
        targetExclude("**/build/**/*.kt")
        ktlint(ktlintVersion).editorConfigOverride(
            mapOf("android" to "true"),
        )
        licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
    }

    format("kts") {
        target("**/*.kts")
        targetExclude("**/build/**/*.kts")
        // Look for the first line that doesn't have a block comment (assumed to be the license)
        licenseHeaderFile(rootProject.file("spotless/copyright.kts"), "(^(?![\\/ ]\\*).*$)")
    }

    format("xml") {
        target("**/*.xml")
        targetExclude("**/build/**/*.xml")
        // Look for the first XML tag that isn't a comment (<!--) or the xml declaration (<?xml)
        licenseHeaderFile(rootProject.file("spotless/copyright.xml"), "(<[^!?])")
    }
}
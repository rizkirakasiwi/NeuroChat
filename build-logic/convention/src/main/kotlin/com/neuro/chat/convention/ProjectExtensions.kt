package com.neuro.chat.convention

import com.diffplug.gradle.spotless.SpotlessExtension
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun Project.library(name: String) = libs.findLibrary(name).get()
fun Project.plugin(name: String) = libs.findPlugin(name).get().get().pluginId
fun Project.version(name: String) = libs.findVersion(name).get().toString()
/**
 * Get the dynamic version of the project.
 */
val Project.dynamicVersion
    get() = project.version.toString().split('+')[0]

fun DependencyHandlerScope.implementation(library: Provider<MinimalExternalModuleDependency>) {
    add("implementation", library)
}

fun DependencyHandlerScope.implementation(project: Project) {
    add("implementation", project)
}

fun DependencyHandlerScope.debugImplementation(library: Provider<MinimalExternalModuleDependency>) {
    add("debugImplementation", library)
}

fun DependencyHandlerScope.testImplementation(library: Provider<MinimalExternalModuleDependency>) {
    add("testImplementation", library)
}

fun DependencyHandlerScope.androidTestImplementation(library: Provider<MinimalExternalModuleDependency>) {
    add("androidTestImplementation", library)
}

fun DependencyHandlerScope.commonTestImplementation(library: Provider<MinimalExternalModuleDependency>) {
    add("commonTestImplementation", library)
}

fun DependencyHandlerScope.commonMainImplementation(library: Provider<MinimalExternalModuleDependency>) {
    add("commonMainImplementation", library)
}

fun DependencyHandlerScope.commonMainImplementation(project: Project) {
    add("commonMainImplementation", project)
}

fun DependencyHandlerScope.androidMainImplementation(library: Provider<MinimalExternalModuleDependency>) {
    add("androidMainImplementation", library)
}

fun DependencyHandlerScope.androidMainImplementation(project: Project) {
    add("androidMainImplementation", project)
}

fun DependencyHandlerScope.detektPlugins(library: Provider<MinimalExternalModuleDependency>) {
    add("detektPlugins", library)
}

inline fun Project.detektGradle(crossinline configure: DetektExtension.() -> Unit) =
    extensions.configure<DetektExtension> {
        configure()
    }

inline fun Project.spotlessGradle(crossinline configure: SpotlessExtension.() -> Unit) =
    extensions.configure<SpotlessExtension> {
        configure()
    }

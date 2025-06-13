plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.dependencyGuard) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.moduleGraph) apply true
    alias(libs.plugins.ktrofit) apply false
}

object DynamicVersion {
    fun setDynamicVersion(file: File, version: String) {
        val cleanedVersion = version.split('+')[0]
        file.writeText(cleanedVersion)
    }
}

tasks.register("versionFile") {
    val file = File(projectDir, "version.txt")

    DynamicVersion.setDynamicVersion(file, project.version.toString())
}

// Task to print all the module paths in the project e.g. :core:data
// Used by module graph generator script
tasks.register("printModulePaths") {
    subprojects {
        if (subprojects.isEmpty()) {
            println(this.path)
        }
    }
}

// Configuration for CMP module dependency graph
moduleGraphAssert {
    configurations += setOf("commonMainImplementation", "commonMainApi")
    configurations += setOf("androidMainImplementation", "androidMainApi")
    configurations += setOf("desktopMainImplementation", "desktopMainApi")
    configurations += setOf("jsMainImplementation", "jsMainApi")
    configurations += setOf("nativeMainImplementation", "nativeMainApi")
    configurations += setOf("wasmJsMainImplementation", "wasmJsMainApi")
}
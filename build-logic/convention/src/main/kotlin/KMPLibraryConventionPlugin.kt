
import com.android.build.gradle.LibraryExtension
import com.neuro.chat.convention.commonTestImplementation
import com.neuro.chat.convention.configureFlavors
import com.neuro.chat.convention.configureKotlinAndroid
import com.neuro.chat.convention.configureKotlinMultiplatform
import com.neuro.chat.convention.library
import com.neuro.chat.convention.version
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * Plugin that applies the Android library and Kotlin multiplatform plugins and configures them.
 */
class KMPLibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("convention.kmp.koin")
                apply("convention.detekt")
                apply("convention.ktlint")
                apply("convention.spotless")
            }

            configureKotlinMultiplatform()

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = version("targetSdk").toInt()
                configureFlavors(this)
                // The resource prefix is derived from the module name,
                // so resources inside ":core:module1" must be prefixed with "core_module1_"
                resourcePrefix = path
                    .split("""\W""".toRegex())
                    .drop(1).distinct()
                    .joinToString(separator = "_")
                    .lowercase() + "_"
            }

            dependencies {
                commonTestImplementation(library("kotlin.test"))
                commonTestImplementation(library("kotlinx.coroutines.test"))
            }
        }
    }
}
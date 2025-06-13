import com.neuro.chat.convention.androidMainImplementation
import com.neuro.chat.convention.commonMainImplementation
import com.neuro.chat.convention.library
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Plugin that applies the CMP feature plugin and configures it.
 * This plugin applies the following plugins:
 * - org.mifos.kmp.library - Kotlin Multiplatform Library
 * - org.mifos.kmp.koin - Koin for Kotlin Multiplatform
 * - org.jetbrains.kotlin.plugin.compose - Kotlin Compose
 * - org.jetbrains.compose - Compose Multiplatform
 * - org.mifos.detekt.plugin - Detekt Plugin
 * - org.mifos.spotless.plugin - Spotless Plugin
 *
 */
class CMPFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("convention.kmp.library")
                apply("convention.kmp.koin")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.compose")
                apply("convention.detekt")
                apply("convention.ktlint")
                apply("convention.spotless")
            }

            dependencies {
                commonMainImplementation(project(":core:designsystem"))

                commonMainImplementation(library("koin.compose"))
                commonMainImplementation(library("koin.compose.viewmodel"))

                commonMainImplementation(library("jb.composeRuntime"))
                commonMainImplementation(library("jb.composeViewmodel"))
                commonMainImplementation(library("jb.lifecycleViewmodel"))
                commonMainImplementation(library("jb.lifecycle.compose"))
                commonMainImplementation(library("jb.lifecycleViewmodelSavedState"))
                commonMainImplementation(library("jb.savedstate"))
                commonMainImplementation(library("jb.bundle"))
                commonMainImplementation(library("jb.composeNavigation"))
                commonMainImplementation(library("kotlinx.collections.immutable"))

                androidMainImplementation(platform(library("koin-bom")))
                androidMainImplementation(library("koin-android"))
                androidMainImplementation(library("koin.androidx.compose"))

                androidMainImplementation(library("koin.android"))
                androidMainImplementation(library("koin.androidx.navigation"))
                androidMainImplementation(library("koin.androidx.compose"))
                androidMainImplementation(library("koin.core.viewmodel"))

            }
        }
    }
}

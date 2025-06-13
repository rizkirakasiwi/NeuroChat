
import com.neuro.chat.convention.commonMainImplementation
import com.neuro.chat.convention.commonTestImplementation
import com.neuro.chat.convention.library
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Plugin that applies the Koin plugin and configures it.
 */
class KMPKoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }

            dependencies {
                val bom = library("koin-bom")
                commonMainImplementation(platform(bom))
                commonMainImplementation(library("koin.core"))
                commonMainImplementation(library("koin.annotations"))

                commonTestImplementation(library("koin.test"))
            }
        }
    }
}

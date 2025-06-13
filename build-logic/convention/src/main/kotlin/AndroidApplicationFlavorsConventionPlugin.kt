import com.android.build.api.dsl.ApplicationExtension
import com.neuro.chat.convention.configureFlavors
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Plugin that applies the Android application flavors plugin and configures it.
 */
class AndroidApplicationFlavorsConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<ApplicationExtension> {
                configureFlavors(this)
            }
        }
    }
}

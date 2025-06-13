import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.gradle.BaseExtension
import com.neuro.chat.convention.configureBadgingTasks
import com.neuro.chat.convention.configureKotlinAndroid
import com.neuro.chat.convention.configurePrintApksTask
import com.neuro.chat.convention.version
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

/**
 * Plugin that applies the Android application plugin and configures it.
 */
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("com.dropbox.dependency-guard")
                apply("convention.detekt")
                apply("convention.ktlint")
                apply("convention.spotless")
                apply("convention.git.hooks")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = version("targetSdk").toInt()
                testOptions.animationsDisabled = true
            }
            extensions.configure<ApplicationAndroidComponentsExtension> {
                configurePrintApksTask(this)
                configureBadgingTasks(extensions.getByType<BaseExtension>(), this)
            }
        }
    }
}

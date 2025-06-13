import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.neuro.chat.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_21
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
    compileOnly(libs.ktlint.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.spotless.gradle)
    compileOnly(libs.ksp.gradlePlugin)
    implementation(libs.truth)
}

gradlePlugin {
    plugins {
        register("KMPLibrary") {
            id = "convention.kmp.library"
            implementationClass = "KMPLibraryConventionPlugin"
        }
    }
    plugins {
        register("KMPKoin") {
            id = "convention.kmp.koin"
            implementationClass = "KMPKoinConventionPlugin"
        }
    }
    plugins {
        register("CMPFeature") {
            id = "convention.cmp.feature"
            implementationClass = "CMPFeatureConventionPlugin"
        }
    }

    plugins {
        register("AndroidApplicationCompose") {
            id = "convention.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
    }
    plugins {
        register("AndroidApplication") {
            id = "convention.application.android"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
    }
    plugins {
        register("AndroidApplicationFavors") {
            id = "convention.application.flavors"
            implementationClass = "AndroidApplicationFlavorsConventionPlugin"
        }
    }
    plugins {
        register("detektPlugin") {
            id = "convention.detekt"
            implementationClass = "DetektConventionPlugin"
        }
    }
    plugins {
        register("ktlintPlugin") {
            id = "convention.ktlint"
            implementationClass = "KtlintConventionPlugin"
        }
    }
    plugins {
        register("spotlessPlugin") {
            id = "convention.spotless"
            implementationClass = "SpotlessConventionPlugin"
        }
    }
    plugins {
        register("gitHooksPlugin") {
            id = "convention.git.hooks"
            implementationClass = "GitHooksConventionPlugin"
        }
    }
}


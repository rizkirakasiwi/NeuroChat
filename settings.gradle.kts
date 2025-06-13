rootProject.name = "NeuroChat"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
check(JavaVersion.current().isCompatibleWith(JavaVersion.VERSION_21)) {
    """
    This project requires JDK 21+ but it is currently using JDK ${JavaVersion.current()}.
    Java Home: [${System.getProperty("java.home")}]
    https://developer.android.com/build/jdks#jdk-config-in-studio
    """.trimIndent()
}


include(":appNavigation")
include(":core:designsystem")
include(":core:model")
include(":core:data")
include(":core:common")
include(":core:datastore")
include(":appShared")
include(":core:platform")
include(":core:ui")
include(":appAndroid")
include(":appDesktop")
include(":appWebsite")

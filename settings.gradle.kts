pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include(":kbindgen-generator", ":kbindgen-gradle-plugin")
rootProject.name = "kbindgen"

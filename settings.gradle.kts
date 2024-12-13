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

include(":kbindgen-generator", ":kbindgen-gradle-plugin", ":kbindgen-runtime")
rootProject.name = "kbindgen"

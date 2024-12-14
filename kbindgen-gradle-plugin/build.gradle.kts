import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.shadow).apply(false)
    id("maven-publish")
    `java-gradle-plugin`
}

group = "de.cacheoverflow.kbindgen"
version = libs.versions.kbindgen.get()

val projectJvmTarget = libs.versions.jvmTarget.get()
java {
    sourceCompatibility = JavaVersion.valueOf("VERSION_${projectJvmTarget}")
    targetCompatibility = JavaVersion.valueOf("VERSION_${projectJvmTarget}")
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        jvmTarget.set(JvmTarget.valueOf("JVM_$projectJvmTarget"))
    }
}

tasks.withType<ProcessResources> {
    from(project(":kbindgen-generator").layout.buildDirectory.file("libs/kbindgen-generator-1.0.0.jar").get().asFile) {
        rename("kbindgen-generator-$version.jar", "binding-generator-executable.jar")
    }
}

gradlePlugin {
    plugins {
        create("bindgen-plugin") {
            id = "de.cacheoverflow.kbindgen"
            implementationClass = "de.cacheoverflow.kbindgen.gradle.BindgenGradlePlugin"
            displayName = "KBindgen"
            description = "Gradle Plugin for the Kotlin-Multiplatform binding generator KBindgen"
            tags.set(listOf("bindings", "kotlin", "jni"))
            version = libs.versions.kbindgen.get()
        }
    }
}

dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly(gradleApi())
    compileOnly(libs.kotlin.plugin)
}

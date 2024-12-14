import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.shadow)
    id("maven-publish")
    application
}

group = "de.cacheoverflow.kbindgen"
version = libs.versions.kbindgen.get()

application {
    mainClass.set("de.cacheoverflow.kbindgen.generator.BindingGeneratorKt")
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("")
    manifest {
        attributes(mapOf("Main-Class" to "de.cacheoverflow.kbindgen.generator.BindingGeneratorKt"))
    }
    mergeServiceFiles()
}

kotlin {
    jvm {
        withJava()
    }
    sourceSets {
        commonMain.dependencies {
            fun addLWJGLDependency(dependency: Provider<MinimalExternalModuleDependency>) {
                implementation(dependency)
                
                // Linux natives
                runtimeOnly(dependencies.variantOf(dependency) { classifier("natives-linux-arm64") })
                runtimeOnly(dependencies.variantOf(dependency) { classifier("natives-linux") })
                
                // macOS natives
                runtimeOnly(dependencies.variantOf(dependency) { classifier("natives-macos-arm64") })
                runtimeOnly(dependencies.variantOf(dependency) { classifier("natives-macos") })
                
                // Windows natives
                runtimeOnly(dependencies.variantOf(dependency) { classifier("natives-windows-arm64") })
                runtimeOnly(dependencies.variantOf(dependency) { classifier("natives-windows") })
            }
            
            addLWJGLDependency(libs.lwjgl.base)
            addLWJGLDependency(libs.lwjgl.llvm)
        }
    }
}

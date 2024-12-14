plugins {
    alias(libs.plugins.kotlin.multiplatform)
    id("maven-publish")
}

group = "de.cacheoverflow.kbindgen"
version = libs.versions.kbindgen.get()

kotlin {
    jvm()
    sourceSets {
        commonMain.dependencies {
            fun addLWJGLDependency(dependency: Provider<MinimalExternalModuleDependency>) {
                implementation(dependency)
                
                // Linux natives
                runtimeOnly(dependencies.variantOf(dependency) { classifier("natives-linux-arm64") })
                runtimeOnly(dependencies.variantOf(dependency) { classifier("natives-linux-riscv64") })
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

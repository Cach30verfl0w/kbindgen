plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm()
    listOf(linuxX64(), linuxArm64(), macosX64(), macosArm64(), mingwX64()).forEach {
        it.compilations.configureEach {
            cinterops {
                val dlfcn by creating {}
            }
        }
    }
    applyDefaultHierarchyTemplate()
    
    sourceSets {
        nativeMain.dependencies {
        
        }
    }
}

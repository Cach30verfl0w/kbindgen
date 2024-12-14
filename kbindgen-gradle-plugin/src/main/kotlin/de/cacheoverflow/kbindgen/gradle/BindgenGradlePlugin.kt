/*
 * Copyright 2024 Cedric Hammes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.cacheoverflow.kbindgen.gradle

import de.cacheoverflow.kbindgen.generator.util.LLVMHelper
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class BindgenGradlePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.create("kbindgen", BindgenExtension::class.java).also { extension ->
            extension.llvmFolder.set(LLVMHelper.getBaseDirectory().toFile())
        }
        
        val kotlinExtension = project.extensions.findByType(KotlinMultiplatformExtension::class.java)
            ?: throw GradleException("Unable to find Kotlin multiplatform extension in project '${project.displayName}'")
        
        project.afterEvaluate {
            // TODO: Create everything for all targets
            kotlinExtension.targets.forEach { println(it.name) }
        }
    }
}

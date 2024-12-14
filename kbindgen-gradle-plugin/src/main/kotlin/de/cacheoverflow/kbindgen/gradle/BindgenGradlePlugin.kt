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

import de.cacheoverflow.kbindgen.gradle.tasks.BindingCodeGenerationTask
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

class BindgenGradlePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        
        // Create and configure Gradle extension for kbindgen
        val bindGenExtension = project.extensions.create("kbindgen", BindgenExtension::class.java).also { extension ->
            extension.generatedSourcesFolder.set(project.layout.buildDirectory.dir("generated/bindings"))
            extension.bindings.configureEach { binding ->
                binding.generatedSourcesFolder.convention(extension.generatedSourcesFolder.dir(binding.name))
                project.tasks.register("${binding.name}CreateKNBindingCode", BindingCodeGenerationTask::class.java) { task ->
                    task.group = "kbindgen"
                    task.description = "Generate Kotlin/Native source files for ${binding.name} bindings"
                    task.outputFolder.set(binding.generatedSourcesFolder)
                    task.binding.set(binding)
                }
            }
        }
        
        // TODO: Unpack task
        
        val kotlinExtension = project.extensions.findByType(KotlinMultiplatformExtension::class.java)
            ?: throw GradleException("Unable to find Kotlin multiplatform extension in project '${project.displayName}'")
    
        project.afterEvaluate {
            if (kotlinExtension.targets.none { it is KotlinNativeTarget })
                throw GradleException("No Kotlin/Native targets are defined")
            
            for (binding in bindGenExtension.bindings) {
                if (binding.headers.get().isEmpty()) {
                    throw GradleException("'${binding.name}' binding has no headers defined")
                }
            }
            // TODO: Throw exception if there are no native targets
        }
    }
}

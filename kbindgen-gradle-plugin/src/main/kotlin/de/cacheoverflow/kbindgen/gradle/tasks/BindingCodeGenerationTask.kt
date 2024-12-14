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

package de.cacheoverflow.kbindgen.gradle.tasks

import de.cacheoverflow.kbindgen.gradle.Binding
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

open class BindingCodeGenerationTask : DefaultTask() {
    private val objects: ObjectFactory = project.objects
    @OutputDirectory
    internal val outputFolder: DirectoryProperty = objects.directoryProperty()
    internal val binding: Property<Binding> = objects.property(Binding::class.java)
    
    @TaskAction
    fun runTask() {
        val binding = binding.get()
        // TODO: headers to path
        // TODO: Replace headers with java.nio.Path (we need to acquire a list of default include paths before we do that)
    }
}

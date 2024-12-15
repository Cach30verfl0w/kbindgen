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

package de.cacheoverflow.kbindgen.generator

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.options.versionOption
import com.github.ajalt.clikt.parameters.types.boolean
import de.cacheoverflow.kbindgen.generator.util.LLVMHelper
import de.cacheoverflow.kbindgen.generator.util.folder
import de.cacheoverflow.kbindgen.generator.util.path
import java.nio.file.Path

// TODO: If finished with JVM bindings, work on critical calls for JNI as EXPERIMENTAL option
class ApplicationCommand : CliktCommand("KBindgen Generator") {
    override fun help(context: Context): String = """
        KBindgen Generator is used to generate Kotlin bindings for native C APIs with support for Kotlin/Native and
        Kotlin/JVM bindings. It also can be used to generate prepared Gradle projects for the bindings with the
        parameters specified. The generated Kotlin/Native bindings are a generated wrapper around a cross-platform
        dlfcn API. If JVM bindings enabled, the native bindings contain a JNI wrapper around the native functions and
        these are used in the JVM bindings.
    """.trimIndent()
    
    private val jvmBindings: Boolean by option(help = "Generate additionally the sources for JVM bindings (default false)").boolean()
        .default(false)
    private val includeFolder: List<Path> by option(help = "Folder referencing to the include folders (optional, multiple)").folder(true)
        .multiple()
    private val headers: List<String> by option(help = "Relative path to the header files to parse (optional)").multiple()
    private val logFile: Path? by option(help = "Path to the log files saving the terminal log of the application").path(mustNotExists = true)
    
    override fun run() {}
}

class BindingsCommand : CliktCommand("bindings") {
    override fun help(context: Context): String = "Generate the binding source code into the specified folder"
    
    private val outputFolder: Path by option(help = "Folder to generated output files").path(mustNotExists = true).required()
    private val llvmFolder: Path? by option(help = "Folder to custom LLVM installation (optional)").folder(true)
        .default(LLVMHelper.getBaseDirectory())
    
    override fun run() = throw UnsupportedOperationException("Not implemented yet")
}

class ProjectCommand : CliktCommand("project") {
    override fun help(context: Context): String = "Command for the creation of a prepared basic Gradle projects for own bindings"
    
    private val projectFolder: Path by option(help = "Folder of the Gradle project to be created").path(mustNotExists = true)
        .default(Path.of("./"))
    private val targets: List<String> by option(help = "Kotlin/Native targets required to support").multiple() // TODO: Replace with enum
    
    override fun run() = throw UnsupportedOperationException("Not implemented yet")
}

fun main(args: Array<String>) = ApplicationCommand()
    .versionOption("1.0.0")
    .subcommands(ProjectCommand(), BindingsCommand())
    .main(args)

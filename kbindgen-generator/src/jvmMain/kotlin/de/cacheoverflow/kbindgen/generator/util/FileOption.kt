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

package de.cacheoverflow.kbindgen.generator.util

import com.github.ajalt.clikt.completion.CompletionCandidates
import com.github.ajalt.clikt.parameters.options.NullableOption
import com.github.ajalt.clikt.parameters.options.RawOption
import com.github.ajalt.clikt.parameters.options.convert
import java.nio.file.FileSystem
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.absolutePathString

fun RawOption.path(
    mustExists: Boolean = false,
    mustNotExists: Boolean = false,
    canBeDirectory: Boolean = false,
    canBeFile: Boolean = false,
    fileSystem: FileSystem = FileSystems.getDefault()
): NullableOption<Path, Path> =
    convert({ localization.pathMetavar() }, CompletionCandidates.Path) { string ->
        with (context.localization) {
            val path = fileSystem.getPath(string)
            if (!Files.isDirectory(path)) fail("Path \"${path.absolutePathString()}\" is not a directory.")
            if (mustExists && !Files.exists(path)) fail(pathDoesNotExist("Folder", path.absolutePathString()))
            if (!mustNotExists && Files.exists(path)) fail("Path \"${path.absolutePathString()}\" does exists.")
            if (!canBeDirectory && Files.isDirectory(path)) fail(pathIsDirectory("Path", path.absolutePathString()))
            if (!canBeFile && Files.isRegularFile(path)) fail(pathIsFile("Path", path.absolutePathString()))
            path
        }
    }

fun RawOption.folder(mustExists: Boolean): NullableOption<Path, Path> = path(mustExists, false, true, false)
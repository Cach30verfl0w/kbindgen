/**
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

import de.cacheoverflow.kbindgen.generator.util.LLVMHelper

fun main() {
    val llvmBaseDirectory = LLVMHelper.getBaseDirectory()
    if (LLVMHelper.loadLibrary(llvmBaseDirectory)) {
        println("LLVM native loaded successfully")
    }
    
    val includeFolder = llvmBaseDirectory.resolve("include")
    val analyzer = HeaderAnalyzer(
        listOf(includeFolder.resolve("llvm-c").resolve("Core.h")),
        listOf(includeFolder)
    )
    analyzer.functions.forEach { println(it.generateJNIGlueCode("allahalala")) }
}

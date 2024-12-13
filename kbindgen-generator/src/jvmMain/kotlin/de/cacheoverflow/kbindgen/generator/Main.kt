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
    analyzer.functions.forEach { println(it) }
}

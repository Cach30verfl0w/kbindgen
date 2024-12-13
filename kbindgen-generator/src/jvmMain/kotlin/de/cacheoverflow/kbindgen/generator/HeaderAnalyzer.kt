package de.cacheoverflow.kbindgen.generator

import de.cacheoverflow.kbindgen.generator.type.Type
import de.cacheoverflow.kbindgen.generator.util.LLVMHelper
import org.lwjgl.llvm.CXCursor
import org.lwjgl.llvm.CXCursorVisitorI
import org.lwjgl.llvm.CXSourceLocation
import org.lwjgl.llvm.CXString
import org.lwjgl.llvm.CXType
import org.lwjgl.llvm.ClangIndex
import org.lwjgl.system.MemoryStack
import java.nio.file.Path
import kotlin.io.path.absolutePathString

/**
 * This class is the implementation of the class for analyzing and modelling C file headers into a Kotlin model ready for the generation of
 * Kotlin to C bindings. It uses Clang to analyze the headers, so the developer needs to initialize LLVM with the [LLVMHelper] util.
 *
 * @author Cedric Hammes
 * @since  12/12/2024
 */
class HeaderAnalyzer(targetHeaderFiles: List<Path>, val includeFolders: List<Path>) : CXCursorVisitorI {
    private val skipHeaderFiles: MutableList<Path> = mutableListOf()
    
    init {
        if (!LLVMHelper.isLLVMLoaded) {
            throw IllegalStateException("LLVM is not initialized yet, please call LLVMHelper.loadLibrary with the LLVM path to fix this")
        }
        
        val index = requireNotNull(ClangIndex.clang_createIndex(false, false))
        MemoryStack.stackPush().use { stack ->
            val argsBuffer = stack.mallocPointer(includeFolders.size)
            for (index in 0..<includeFolders.size) {
                argsBuffer.put(index, stack.UTF8("-I${includeFolders[index].absolutePathString()}"))
            }
            
            for (header in targetHeaderFiles) {
                if (header in skipHeaderFiles)
                    continue
                
                val translationUnit = ClangIndex.clang_parseTranslationUnit(
                    index, header.absolutePathString(), argsBuffer, null,
                    ClangIndex.CXTranslationUnit_None
                )
                val rootCursor = CXCursor.create().also { ClangIndex.clang_getTranslationUnitCursor(translationUnit, it) }
                ClangIndex.clang_visitChildren(rootCursor, this, 0)
                rootCursor.close()
                ClangIndex.clang_disposeTranslationUnit(translationUnit)
            }
        }
        ClangIndex.clang_disposeIndex(index)
    }
    
    override fun invoke(current: CXCursor, parentCursor: CXCursor, ignored: Long): Int {
        val headerPath = CXSourceLocation.create().use { sourceLocation ->
            ClangIndex.clang_getCursorLocation(current, sourceLocation)
            Path.of(requireNotNull(MemoryStack.stackPush().use { stack ->
                val file = stack.mallocPointer(1)
                ClangIndex.clang_getFileLocation(sourceLocation, file, null, null, null)
                CXString.create().use { ClangIndex.clang_getCString(ClangIndex.clang_getFileName(file.get(0), it)) }
            }))
        }.also { skipHeaderFiles.add(it) }
        
        // Process different operations
        when (ClangIndex.clang_getCursorKind(current)) {
            /*ClangIndex.CXCursor_TypedefDecl -> {
                val underlyingType = CXType.create().use { ClangIndex.clang_getTypedefDeclUnderlyingType(current, it).getSpelling() }
                val type = current.getSpelling()
            }*/
            
            ClangIndex.CXCursor_FunctionDecl -> {
                val functionName = current.getSpelling()
                val functionBuilder = StringBuilder().append(functionName).append("(")
                
                // Analyze function arguments and return type
                CXType.create().also { ClangIndex.clang_getCursorType(current, it) }.use { functionType ->
                    for (i in 0..<ClangIndex.clang_getNumArgTypes(functionType)) {
                        val argumentType = CXType.create().use { Type.from(ClangIndex.clang_getArgType(functionType, i, it)) }
                            ?: return ClangIndex.CXChildVisit_Recurse
                        functionBuilder.append(", ").append(argumentType.kotlinNativeSpelling)
                    }
                    
                    // Analyze return type
                    val returnType = CXType.create().use { Type.from(ClangIndex.clang_getResultType(functionType, it)) }
                        ?: return ClangIndex.CXChildVisit_Recurse
                    functionBuilder.append("): ").append(returnType.kotlinNativeSpelling)
                    println(functionBuilder.toString().replaceFirst(", ", ""))
                }
            }
            
            else -> {}
        }
        return ClangIndex.CXChildVisit_Recurse
    }
    
    private fun CXCursor.getSpelling(): String = requireNotNull(CXString.create()
        .use { ClangIndex.clang_getCString(ClangIndex.clang_getCursorSpelling(this, it)) })
}
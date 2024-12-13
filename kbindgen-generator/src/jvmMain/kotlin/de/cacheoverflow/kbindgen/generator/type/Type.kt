package de.cacheoverflow.kbindgen.generator.type

import org.lwjgl.llvm.CXString
import org.lwjgl.llvm.CXType
import org.lwjgl.llvm.ClangIndex
import kotlin.use

/**
 * @author Cedric Hammes
 * @since  13/12/2024
 */
interface Type {
    val headerSpelling: String
    val kotlinNativeSpelling: String
    val kotlinNativePtrSpelling: String
    val kotlinJvmSpelling: String
    val isPointer: Boolean
    val isOpaque: Boolean
    val modifier: Modifier
    
    fun makePointer(modifier: Modifier = Modifier.NONE): Type = PointerType(this, modifier)
    fun withModifier(modifier: Modifier): Type = WithModifierType(modifier, this)
    
    companion object {
        fun from(type: CXType): Type? = when (type.kind()) { // TODO: Add support for const modifier
            ClangIndex.CXType_Char16, ClangIndex.CXType_Char_S -> StandardType.BYTE
            ClangIndex.CXType_Long, ClangIndex.CXType_LongLong -> StandardType.LONG
            ClangIndex.CXType_ULong, ClangIndex.CXType_ULongLong -> StandardType.ULONG
            ClangIndex.CXType_Double -> StandardType.DOUBLE
            ClangIndex.CXType_Float -> StandardType.FLOAT
            ClangIndex.CXType_Short -> StandardType.SHORT
            ClangIndex.CXType_Void -> StandardType.VOID
            ClangIndex.CXType_Int -> StandardType.INT
            ClangIndex.CXType_UChar -> StandardType.UBYTE
            ClangIndex.CXType_UShort -> StandardType.USHORT
            ClangIndex.CXType_UInt -> StandardType.UINT
            ClangIndex.CXType_Pointer -> CXType.create().use { from(ClangIndex.clang_getPointeeType(type, it)) }?.makePointer()
            ClangIndex.CXType_Elaborated -> CXType.create().use { from(ClangIndex.clang_getCanonicalType(type, it)) }
            ClangIndex.CXType_Record -> StandardType.VOID.makePointer()
            ClangIndex.CXType_Enum -> null // TODO: Enum types are undefined in C. I need to find some generation.
            ClangIndex.CXType_FunctionProto -> null // TODO: Closures
            ClangIndex.CXType_IncompleteArray, ClangIndex.CXType_VariableArray, ClangIndex.CXType_DependentSizedArray -> null // TODO: Add support
            else -> throw UnsupportedOperationException("Unsupported type (kind = ${type.kind()}): ${type.getSpelling()}")
        }
        
        private fun CXType.getSpelling(): String = requireNotNull(
            CXString.create()
                .use { ClangIndex.clang_getCString(ClangIndex.clang_getTypeSpelling(this, it)) })
    }
}

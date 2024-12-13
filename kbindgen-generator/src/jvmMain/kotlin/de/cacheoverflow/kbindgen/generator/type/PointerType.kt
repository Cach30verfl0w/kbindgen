package de.cacheoverflow.kbindgen.generator.type

class PointerType(pointedType: Type, override val modifier: Modifier) : Type {
    override val isPointer: Boolean = true
    override val isOpaque: Boolean = pointedType.isOpaque || pointedType == StandardType.VOID // TODO: Or type is void/unresolved
    override fun toString(): String = kotlinNativeSpelling
    
    override val headerSpelling: String = "jlong"
    override val kotlinNativeSpelling: String = if (pointedType == StandardType.VOID || isOpaque)
        "kotlinx.cinterop.COpaquePointer"
    else
        "${if (Modifier.CONST in modifier) "kotlinx.cinterop.CPointerVar" else "kotlinx.cinterop.CPointer"}<${pointedType.kotlinNativePtrSpelling}>"
    override val kotlinNativePtrSpelling: String = kotlinNativeSpelling
    override val kotlinJvmSpelling: String = "long"
}

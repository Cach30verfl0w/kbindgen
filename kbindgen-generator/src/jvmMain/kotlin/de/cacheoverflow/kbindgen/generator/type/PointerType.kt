package de.cacheoverflow.kbindgen.generator.type

class PointerType(private val pointedType: Type, override val modifier: Modifier) : Type {
    override val headerSpelling: String = "${pointedType.headerSpelling}*"
    override val kotlinNativeSpelling: String = if (pointedType == StandardType.VOID)
        "kotlinx.cinterop.COpaquePointer"
    else
        "${if (Modifier.CONST in modifier) "kotlinx.cinterop.CPointerVar" else "kotlinx.cinterop.CPointer"}<${pointedType.kotlinNativePtrSpelling}>"
    override val kotlinNativePtrSpelling: String = kotlinNativeSpelling
    override val kotlinJvmSpelling: String = "long"
    override val isPointer: Boolean = true
    override val isOpaque: Boolean = pointedType.isOpaque // TODO: Or type is void/unresolved
}

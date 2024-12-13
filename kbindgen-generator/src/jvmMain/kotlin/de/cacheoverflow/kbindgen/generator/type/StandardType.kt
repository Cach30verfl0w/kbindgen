package de.cacheoverflow.kbindgen.generator.type

enum class StandardType(
    override val headerSpelling: String, override val kotlinJvmSpelling: String,
    override val kotlinNativeSpelling: String, override val kotlinNativePtrSpelling: String,
    override val isOpaque: Boolean, override val isPointer: Boolean
) : Type {
    DOUBLE("jdouble", "Double", "Double", "kotlinx.cinterop.DoubleVar", false, false),
    FLOAT("jfloat", "Float", "Float", "kotlinx.cinterop.FloatVar", false, false),
    
    BYTE("jbyte", "Byte", "Byte", "kotlinx.cinterop.ByteVar", false, false),
    SHORT("jshort", "Short", "Short", "kotlinx.cinterop.ShortVar", false, false),
    INT("jint", "Int", "Int", "kotlinx.cinterop.IntVar", false, false),
    LONG("jlong", "Long", "Long", "kotlinx.cinterop.LongVar", false, false),
    
    UBYTE("jbyte", "UByte", "UByte", "kotlinx.cinterop.UByteVar", false, false),
    USHORT("jshort", "UShort", "UShort", "kotlinx.cinterop.UShortVar", false, false),
    UINT("jint", "UInt", "UInt", "kotlinx.cinterop.UIntVar", false, false),
    ULONG("jlong", "ULong", "ULong", "kotlinx.cinterop.ULongVar", false, false),
    VOID("void", "Unit", "Unit", "Unit", false, false);
    
    override val modifier: Modifier = Modifier.NONE
    override fun toString(): String = kotlinJvmSpelling
}

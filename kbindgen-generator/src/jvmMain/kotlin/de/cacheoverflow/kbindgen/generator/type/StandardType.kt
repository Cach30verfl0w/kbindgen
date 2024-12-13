package de.cacheoverflow.kbindgen.generator.type

enum class StandardType(
    override val headerSpelling: String, override val kotlinJvmSpelling: String,
    override val kotlinNativeSpelling: String, override val kotlinNativePtrSpelling: String,
    override val isOpaque: Boolean, override val isPointer: Boolean
) : Type {
    DOUBLE("double", "Double", "Double", "kotlinx.cinterop.DoubleVar", false, false),
    FLOAT("float", "Float", "Float", "kotlinx.cinterop.FloatVar", false, false),
    
    BYTE("int8_t", "Byte", "Byte", "kotlinx.cinterop.ByteVar", false, false),
    SHORT("int16_t", "Short", "Short", "kotlinx.cinterop.ShortVar", false, false),
    INT("int32_t", "Int", "Int", "kotlinx.cinterop.IntVar", false, false),
    LONG("int64_t", "Long", "Long", "kotlinx.cinterop.LongVar", false, false),
    
    UBYTE("uint8_t", "UByte", "UByte", "kotlinx.cinterop.UByteVar", false, false),
    USHORT("uint16_t", "UShort", "UShort", "kotlinx.cinterop.UShortVar", false, false),
    UINT("uint32_t", "UInt", "UInt", "kotlinx.cinterop.UIntVar", false, false),
    ULONG("uint64_t", "ULong", "ULong", "kotlinx.cinterop.ULongVar", false, false),
    VOID("void", "Unit", "Unit", "Unit", false, false);
    
    override val modifier: Modifier = Modifier.NONE
}

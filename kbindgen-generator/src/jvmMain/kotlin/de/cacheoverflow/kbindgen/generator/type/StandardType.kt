package de.cacheoverflow.kbindgen.generator.type

enum class StandardType(override val headerSpelling: String, override val kotlinSpelling: String) : Type {
    DOUBLE("jdouble", "Double"),
    FLOAT("jfloat", "Float"),
    
    BYTE("jbyte", "Byte"),
    SHORT("jshort", "Short"),
    INT("jint", "Int"),
    LONG("jlong", "Long"),
    
    UBYTE("jbyte", "UByte"),
    USHORT("jshort", "UShort"),
    UINT("jint", "UInt"),
    ULONG("jlong", "ULong"),
    VOID("void", "Unit");
    
    override val modifier: Modifier = Modifier.NONE
    override fun toString(): String = kotlinSpelling
}

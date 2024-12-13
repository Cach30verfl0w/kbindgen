package de.cacheoverflow.kbindgen.generator.type

class PointerType(override val modifier: Modifier) : Type {
    override val headerSpelling: String = "jlong"
    override val kotlinSpelling: String = "Long"
    override fun toString(): String = kotlinSpelling
}

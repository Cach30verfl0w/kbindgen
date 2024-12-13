package de.cacheoverflow.kbindgen.generator.type

class WithModifierType(override val modifier: Modifier, private val underlyingType: Type) : Type by underlyingType {
    override fun toString(): String = underlyingType.toString()
}


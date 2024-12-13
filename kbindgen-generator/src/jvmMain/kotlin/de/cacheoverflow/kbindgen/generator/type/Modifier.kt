package de.cacheoverflow.kbindgen.generator.type

@JvmInline
value class Modifier private constructor(private val value: UShort) {
    operator fun plus(other: Modifier): Modifier = Modifier(value or other.value)
    operator fun contains(child: Modifier): Boolean = (value and child.value) == child.value
    
    companion object {
        val NONE: Modifier = Modifier(0b0U)
        val CONST: Modifier = Modifier(0b0000_0000_0000_0001U)
    }
}

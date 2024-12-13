package de.cacheoverflow.kbindgen.generator.models

import de.cacheoverflow.kbindgen.generator.type.Type

data class FunctionModel(val name: String, val parameters: List<Type>, val returnType: Type) {
    override fun toString(): String = "$name(${parameters.joinToString(", ")}): $returnType"
}

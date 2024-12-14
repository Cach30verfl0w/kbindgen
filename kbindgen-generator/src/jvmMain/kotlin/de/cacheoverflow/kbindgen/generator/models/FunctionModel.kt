/**
 * Copyright 2024 Cedric Hammes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.cacheoverflow.kbindgen.generator.models

import de.cacheoverflow.kbindgen.generator.type.Type

data class FunctionModel(val name: String, val parameters: List<Type>, val returnType: Type) {
    
    // Kotlin/Native function reference generation
    fun generateKotlinNativeTypeAlias(): String =
        "internal typealias ${name}Func = (${parameters.joinToString(", ") { it.kotlinSpelling }}) -> ${returnType.kotlinSpelling}"
    
    fun generateFunctionField(): String =
        "private val func$name: CPointer<CFunction<${name}Func>> by lazy { library.findFunction(\"$name\") }"
    
    override fun toString(): String = "$name(${parameters.joinToString(", ")}): $returnType"
}

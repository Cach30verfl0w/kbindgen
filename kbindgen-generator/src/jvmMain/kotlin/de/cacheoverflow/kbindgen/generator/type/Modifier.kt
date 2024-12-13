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

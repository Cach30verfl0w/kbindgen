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

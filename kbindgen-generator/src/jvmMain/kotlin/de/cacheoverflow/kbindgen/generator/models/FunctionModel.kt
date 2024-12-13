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
    
    /*fun generateJNIGlueCode(javaClass: String): String {
        val functionBuilder = StringBuilder()
        /*functionBuilder
            .append("JNIEXPORT ${returnType.headerSpelling} JNICALL Java_${javaClass.replace(".", "_")}_$name(")
            .append("JNIEnv* env, jlong ptr")
            .append(parameters.joinToString("") { ", ${it.headerSpelling} var_${parameterCounter++}" })
            .append(") {\n")
        functionBuilder.append("    *reinterpret_cast<$name>(ptr)(${})\n")
        functionBuilder.append("}\n")*/
        
        // JNIEXPORT auto JNICALL Java_com_example_project_test_wrap(JNIEnv* env, jlong ptr, jlong test) -> jlong {
        //    return *reinterpret_cast<test>(ptr)(reinterpret_cast<char*>(test)); // TODO: Reinterpret cast for return output?
        //}
        return functionBuilder.toString()
    }*/
    
    override fun toString(): String = "$name(${parameters.joinToString(", ")}): $returnType"
}

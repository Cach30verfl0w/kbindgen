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

#include "dlfcnBridge.h"
#include <dlfcn.h>

JNIEXPORT jlong JNICALL Java_de_cacheoverflow_kbindgen_runtime_LibLoaderWrapper_dlopen(JNIEnv* env, jstring path, jint flags) {
    const char* nativePath = env->GetStringUTFChars(path, nullptr);
    const void* handle = dlopen(path, flags);
    env->ReleaseStringUTFChars(path, nativePath);
    return reinterpret_cast<jlong>(handle);
}

JNIEXPORT jlong JNICALL Java_de_cacheoverflow_kbindgen_runtime_LibLoaderWrapper_dlsym(JNIEnv* env, jlong handle, jstring name) {
    const char* nativePath = env->GetStringUTFChars(path, nullptr);
    const void* handle = dlsym(reinterpret_cast<void*>(handle), nativePath);
    env->ReleaseStringUTFChars(path, nativePath);
    return reinterpret_cast<jlong>(handle);
}

JNIEXPORT jint JNICALL Java_de_cacheoverflow_kbindgen_runtime_LibLoaderWrapper_dlclose(JNIEnv* env, jlong handle) {
    return dlclose(reinterpret_cast<void*>(handle));
}


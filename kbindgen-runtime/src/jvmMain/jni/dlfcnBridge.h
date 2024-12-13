#pragma once

#include <jni.h>

JNIEXPORT jlong JNICALL Java_de_cacheoverflow_kbindgen_runtime_DLFCN_dlopen(JNIEnv* env, jstring path);
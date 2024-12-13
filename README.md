# KBindgen

KBindgen is an **WIP** library which allows developers to generate native bindings for Kotlin/Native and Kotlin/JVM that can be loaded dynamically at runtime for Kotlin-Multiplatform by specifying C headers (with Clang's tooling library). This code comes in the following modules:
- (embeddable) generator application (used in the Gradle plugin)
- Gradle plugin wrapping the generation tooling and some parts of the compilation (especially for the JNI glue code)

## How does this work?
The core element of KBindgen is the LLVM compiler toolchain, especially Clang's tooling library. It analyzes the 
specified headers and converts them into convertible object models. These models can be translated into
- Kotlin/JVM code with JNI interop glue code for calling these functions by pointer,
- Kotlin/Native code with CInterop types for some compatibility.

## Dependencies
Without the wonderful work of the following people, my work on this project would have been much more difficult.
- The `dflcn` CInterop logic of KBindgen is directly copied from [multiplatform-dlfcn](https://git.karmakrafts.dev/kk/multiplatform-dlfcn) by [KitsuneAlex](https://github.com/KitsuneAlex) (his project is licensed under [Apache License 2.0](https://git.karmakrafts.dev/kk/multiplatform-dlfcn/-/blob/master/LICENSE?ref_type=heads))
- The [LWJGL3](https://github.com/LWJGL/lwjgl3/blob/master/LICENSE.md) LLVM bindings by [the LWJGL team](https://github.com/LWJGL) (this project is licensed under the [BSD 3-Clause License](https://github.com/LWJGL/lwjgl3/blob/master/LICENSE.md))

## License
```
Copyright 2024 Cedric Hammes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


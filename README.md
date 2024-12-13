# KBindgen

KBindgen is an **WIP** library which allows developers to generate native bindings for Kotlin/Native and Kotlin/JVM that can be loaded dynamically at runtime for Kotlin-Multiplatform by specifying C headers (with Clang's tooling library). This code comes in the following modules:
- (embeddable) generator application (used in the Gradle plugin)
- Gradle plugin wrapping the generation tooling and some parts of the compilation (especially for the JNI glue code)

## How does this work?
The core element of KBindgen is the LLVM compiler toolchain, especially Clang's tooling library. It analyzes the 
specified headers and converts them into convertible object models. These models can be translated into
- Kotlin/JVM code with JNI interop glue code for calling these functions by pointer,
- Kotlin/Native code with CInterop types for some compatibility.

## License
```
Copyright 2024 Cedric Hammes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


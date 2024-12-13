package de.cacheoverflow.kbindgen.generator.util

import org.lwjgl.llvm.LLVMCore
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.isDirectory

/**
 * @author Cedric Hammes
 * @since  12/12/2024
 */
internal object LLVMHelper {
    private val BASE_PATHS: Array<String> = arrayOf("/usr/share/lib", "/usr/local/opt", "/usr/lib", "/")
    private val SUB_PATHS: Array<String> = arrayOf("llvm-{}/build/Release", "llvm/build/Release", "llvm@{}", "llvm-{}", "llvm")
    private val VERSIONS: ByteArray = byteArrayOf(20, 19, 18, 17, 16)
    var isLLVMLoaded: Boolean = false
        private set
    
    fun loadLibrary(baseDirectory: Path): Boolean {
        System.setProperty("org.lwjgl.librarypath", baseDirectory.resolve("lib").absolutePathString())
        System.setProperty("org.lwjgl.llvm.libname", findLibraryFileName(baseDirectory))
        
        try {
            LLVMCore.getLibrary()
            isLLVMLoaded = true
            return true
        } catch (ex: UnsatisfiedLinkError) {
            ex.printStackTrace()
            return false
        }
    }
    
    fun getBaseDirectory(): Path {
        if (System.getProperty("os.name").contains("Windows")) {
            return Path.of("C:\\Program Files\\LLVM\\bin")
        }
        
        val separator = FileSystems.getDefault().separator
        for (currentBaseDirectory in BASE_PATHS) {
            val baseDirectory = currentBaseDirectory.replace("/", separator)
            for (currentSubDirectory in SUB_PATHS) {
                val subDirectory = currentSubDirectory.replace("/", separator)
                for (currentVersion in VERSIONS) {
                    val path = Path.of(baseDirectory).resolve(subDirectory.replace("{}", "$currentVersion"))
                    if (!Files.exists(path) || !path.isDirectory()) continue
                    return path
                }
            }
        }
        
        throw RuntimeException("Unable to find LLVM installation on system")
    }
    
    fun findLibraryFileName(baseDirectory: Path): String {
        val libraryDirectory = baseDirectory.resolve("lib")
        if (Files.exists(libraryDirectory.resolve("libLLVM.so"))) {
            return "LLVM"
        }
        
        for (version in VERSIONS) {
            val path = libraryDirectory.resolve("libLLVM-$version.so")
            if (!Files.exists(path) || path.isDirectory()) continue
            return "LLVM-$version"
        }
        
        throw RuntimeException("Unable to find LLVM library on installation '${baseDirectory.absolutePathString()}'")
    }
}
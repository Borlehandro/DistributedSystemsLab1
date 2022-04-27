package me.borlehandro.distributed_systems.lab1.dao

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream
import java.io.BufferedInputStream
import java.io.File

class BZip2Decompressor {

    fun getDecompressedInputStream(fileName: String) =
        BZip2CompressorInputStream(BufferedInputStream(File(fileName).inputStream()))
}
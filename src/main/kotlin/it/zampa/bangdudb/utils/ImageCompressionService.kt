package it.zampa.bangdudb.utils

import java.io.File
import java.io.InputStream

interface ImageCompressionService {
	fun compress(fileToCompress: InputStream, fileName: String): File
}
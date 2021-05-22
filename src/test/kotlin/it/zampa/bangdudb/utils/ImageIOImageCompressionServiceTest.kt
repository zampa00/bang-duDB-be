package it.zampa.bangdudb.utils

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isNotEqualTo
import java.io.File
import java.io.FileInputStream

class ImageIOImageCompressionServiceTest {

	val compressionService = ImageIOImageCompressionService()
	val imageToCompress = File("src\\test\\resources\\Card_Pic_124_0002_2.png")

	@Test
	fun `compress image`() {
		val imageCompressed = compressionService.compress(FileInputStream(imageToCompress), "Card_Pic_124_0002_2")
		expectThat(imageCompressed).isNotEqualTo(null)
	}
}
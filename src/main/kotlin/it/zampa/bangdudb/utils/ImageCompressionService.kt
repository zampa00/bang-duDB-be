package it.zampa.bangdudb.utils

import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam
import javax.imageio.stream.ImageOutputStream
import kotlin.math.roundToInt


class ImageCompressionService {

	fun compress(fileToCompress: File): File {
		val resizedImage = resizeImage(ImageIO.read(fileToCompress))

		val compressedImageFile = File("src\\test\\resources\\${fileToCompress.nameWithoutExtension}_lq.jpg")
		val outputStream: OutputStream = FileOutputStream(compressedImageFile)
		val imageOutputStream = ImageIO.createImageOutputStream(outputStream)

		compressImage(resizedImage, imageOutputStream, 0.9f)

		imageOutputStream.close()
		outputStream.close()

		return compressedImageFile
	}

	private fun resizeImage(image: BufferedImage, reduceFactor: Double = 0.5): BufferedImage {
		val newWidth: Int = (image.width * reduceFactor).roundToInt()
		val newHeight: Int = (image.height * reduceFactor).roundToInt()
		val image2 = BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB)
		val g2d: Graphics2D = image2.createGraphics()
		g2d.drawImage(image, 0, 0, newWidth, newHeight, null);
		g2d.dispose()
		return image2
	}

	private fun compressImage(imageToResize: BufferedImage, imageOutputStream: ImageOutputStream?, compressionQuality: Float) {
		val writers = ImageIO.getImageWritersByFormatName("jpg")
		val writer = writers.next()

		writer.output = imageOutputStream

		val writerParameters = writer.defaultWriteParam

		writerParameters.compressionMode = ImageWriteParam.MODE_EXPLICIT
		writerParameters.compressionQuality = compressionQuality
		writer.write(null, IIOImage(imageToResize, null, null), writerParameters)
		writer.dispose()
	}

}
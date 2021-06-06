package it.zampa.bangdudb.delivery.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import it.zampa.bangdudb.domain.service.ImageUploader
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.io.InputStream

class S3ImageUploader(
	val s3Client: AmazonS3,
	val bucketName: String,
	val baseUrl: String,
	val directory: String
) : ImageUploader {

	var logger: Logger = LoggerFactory.getLogger(this::class.java)

	override fun upload(img: File, imageName: String): String {
		logger.info("uploading $imageName to s3")
		s3Client.putObject(bucketName, "${directory}/${imageName}", img)
		return "${baseUrl.withoutLastSlash()}/${directory}/${imageName}"
	}

	override fun upload(img: InputStream, imageName: String): String {
		logger.info("uploading $imageName to s3")
		s3Client.putObject(bucketName, "${directory}/${imageName}", img, ObjectMetadata())
		return "${baseUrl.withoutLastSlash()}/${directory}/${imageName}"
	}

}

private fun String.withoutLastSlash(): String =
	if (this.endsWith("/")) this.substringBeforeLast("/") else this

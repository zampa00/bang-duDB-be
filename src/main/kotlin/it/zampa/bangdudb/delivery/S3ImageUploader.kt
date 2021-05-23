package it.zampa.bangdudb.delivery

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import it.zampa.bangdudb.domain.ImageUploader
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.io.InputStream

class S3ImageUploader(val s3Client: AmazonS3) : ImageUploader {

	var logger: Logger = LoggerFactory.getLogger(this::class.java)

	val bucketName = "bang-dudb-test"
	val baseCardUrl = "https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/"

	override fun uploadCard(img: File, imageName: String): String {
		s3Client.putObject(bucketName, "cards/${imageName}", img)
		return baseCardUrl + imageName
	}

	override fun uploadCard(img: InputStream, imageName: String): String {
		logger.info("uploading ${imageName} to s3")
		s3Client.putObject(bucketName, "cards/${imageName}", img, ObjectMetadata())
		return baseCardUrl + imageName
	}

}
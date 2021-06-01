package it.zampa.bangdudb.delivery.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import it.zampa.bangdudb.domain.service.ImageUploader
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.io.InputStream

class S3ImageUploader(val s3Client: AmazonS3) : ImageUploader {

	var logger: Logger = LoggerFactory.getLogger(this::class.java)

	val bucketName = "bang-dudb-test"
	val baseCardUrl = "https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/"
	val baseBannerUrl = "https://bang-dudb-test.s3-eu-west-1.amazonaws.com/banners/"
	val baseEventUrl = "https://bang-dudb-test.s3-eu-west-1.amazonaws.com/events/"

	override fun uploadCard(img: File, imageName: String): String {
		logger.info("uploading $imageName to s3")
		s3Client.putObject(bucketName, "cards/${imageName}", img)
		return baseCardUrl + imageName
	}

	override fun uploadCard(img: InputStream, imageName: String): String {
		logger.info("uploading $imageName to s3")
		s3Client.putObject(bucketName, "cards/${imageName}", img, ObjectMetadata())
		return baseCardUrl + imageName
	}

	override fun uploadBanner(img: File, imageName: String): String {
		logger.info("uploading $imageName to s3")
		s3Client.putObject(bucketName, "banners/${imageName}", img)
		return baseBannerUrl + imageName
	}

	override fun uploadBanner(img: InputStream, imageName: String): String {
		logger.info("uploading $imageName to s3")
		s3Client.putObject(bucketName, "banners/${imageName}", img, ObjectMetadata())
		return baseBannerUrl + imageName
	}

	override fun uploadEvent(img: File, imageName: String): String {
		logger.info("uploading $imageName to s3")
		s3Client.putObject(bucketName, "events/${imageName}", img)
		return baseEventUrl + imageName
	}

	override fun uploadEvent(img: InputStream, imageName: String): String {
		logger.info("uploading $imageName to s3")
		s3Client.putObject(bucketName, "events/${imageName}", img, ObjectMetadata())
		return baseEventUrl + imageName
	}

}
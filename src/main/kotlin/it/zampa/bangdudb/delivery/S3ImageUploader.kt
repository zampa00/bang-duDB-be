package it.zampa.bangdudb.delivery

import com.amazonaws.services.s3.AmazonS3
import it.zampa.bangdudb.domain.ImageUploader
import org.springframework.web.multipart.MultipartFile

class S3ImageUploader(val s3Client: AmazonS3) : ImageUploader {

	val bucketName = "bang-dudb-test"
	val baseCardUrl = "https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/"

	override fun uploadCard(img: MultipartFile, imageName: String): String {
		val imageToUpload = img.resource.file
		s3Client.putObject(bucketName, "cards/${imageName}", imageToUpload)
		return baseCardUrl + imageName
	}

}
package it.zampa.bangdudb.delivery

import com.amazonaws.services.s3.AmazonS3
import it.zampa.bangdudb.domain.ImageUploader
import org.springframework.web.multipart.MultipartFile

class S3ImageUploader(val s3Client: AmazonS3) : ImageUploader {

	val bucketName = "bang-dudb-test"

	override fun uploadCard(img: MultipartFile, imageName: String) {
		val imageToUpload = img.resource.file
		s3Client.putObject(bucketName, "cards/${imageName}", imageToUpload)
	}

}
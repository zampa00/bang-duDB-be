package it.zampa.bangdudb.delivery

import com.amazonaws.services.s3.AmazonS3
import it.zampa.bangdudb.domain.ImageUploader
import org.springframework.web.multipart.MultipartFile

class S3ImageUploader(val s3Client: AmazonS3) : ImageUploader {

	override fun uploadCard(img: MultipartFile) {
		TODO("Not yet implemented")
	}

}
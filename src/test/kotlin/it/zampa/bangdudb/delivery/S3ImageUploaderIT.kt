package it.zampa.bangdudb.delivery

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import it.zampa.bangdudb.delivery.service.S3ImageUploader
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import java.io.File

class S3ImageUploaderIT {

	val s3Client: AmazonS3 = AmazonS3ClientBuilder.standard()
		.withRegion(Regions.EU_WEST_1)
		.withCredentials(ProfileCredentialsProvider("bang-dudb"))
		.build()

	val imageUploader = S3ImageUploader(s3Client)

	val fileToUpload = File("src\\test\\resources\\test_image.jpg")

	@Test
	fun upload() {
		val imageName = "testingImageName"

		imageUploader.uploadCard(fileToUpload, imageName)

		val objects = s3Client.listObjectsV2("bang-dudb-test", "cards/${imageName}")

		expectThat(objects.objectSummaries).hasSize(1)
		expectThat(objects.objectSummaries.first().key).isEqualTo("cards/${imageName}")

		s3Client.deleteObject("bang-dudb-test", "cards/${imageName}")
	}
}
package it.zampa.bangdudb.delivery

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import it.zampa.bangdudb.delivery.service.S3ImageUploader
import org.junit.jupiter.api.AfterEach
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

	private val testDirectory = "test"
	private val testImage = "test_image.jpg";
	private val bucketName = "bang-dudb-test"
	private val baseCardUrl = "https://bang-dudb-test.s3-eu-west-1.amazonaws.com/"
	private val imageUploader = S3ImageUploader(s3Client, bucketName, baseCardUrl, testDirectory)

	private val fileToUpload = File("src\\test\\resources\\${testImage}")

	@AfterEach
	fun tearDown() {
		s3Client.deleteObject("bang-dudb-test", "${testDirectory}/${testImage}")
	}

	@Test
	fun upload() {
		val resultUrl = imageUploader.upload(fileToUpload, testImage)

		val objects = s3Client.listObjectsV2(bucketName, "${testDirectory}/${testImage}")

		expectThat(objects.objectSummaries).hasSize(1)
		expectThat(objects.objectSummaries.first().key).isEqualTo("${testDirectory}/${testImage}")
		expectThat(resultUrl).isEqualTo("https://bang-dudb-test.s3-eu-west-1.amazonaws.com/test/test_image.jpg")
	}
}
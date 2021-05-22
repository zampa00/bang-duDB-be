package it.zampa.bangdudb.delivery

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.web.multipart.MultipartFile
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

	val file = File("src\\test\\resources\\test_image.jpg")
	val imageToUpload = mock<MultipartFile>()

	@BeforeEach
	internal fun setUp() {
		whenever(imageToUpload.resource).thenReturn(mock())
		whenever(imageToUpload.resource.file).thenReturn(file)
	}

	@Test
	fun upload() {
		val imageName = "testingImageName"
		whenever(imageToUpload.resource).thenReturn(mock())
		whenever(imageToUpload.resource.file).thenReturn(file)

		imageUploader.uploadCard(imageToUpload, imageName)

		val objects = s3Client.listObjectsV2("bang-dudb-test", "cards/${imageName}")

		expectThat(objects.objectSummaries).hasSize(1)
		expectThat(objects.objectSummaries.first().key).isEqualTo("cards/${imageName}")

		s3Client.deleteObject("bang-dudb-test", "cards/${imageName}")
	}
}
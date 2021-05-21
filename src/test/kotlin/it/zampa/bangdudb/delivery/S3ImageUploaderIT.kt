package it.zampa.bangdudb.delivery

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.web.multipart.MultipartFile
import java.io.File

class S3ImageUploaderIT {

	val s3Client: AmazonS3 = AmazonS3ClientBuilder.standard()
		.withRegion(Regions.EU_WEST_1)
		.withCredentials(ProfileCredentialsProvider("bang-dudb"))
		.build()

	val imageUploader = S3ImageUploader(s3Client)


	@Test
	fun upload() {
		val file = File("src\\test\\resources\\test_image.jpg")
		val imageToUpload = mock<MultipartFile>()
		whenever(imageToUpload.resource).thenReturn(mock())
		whenever(imageToUpload.resource.file).thenReturn(file)

//		imageUploader.upload(imageToUpload)

//		s3Client.putObject("bang-dudb-test", "cards/test", file)

		val objects = s3Client.listObjectsV2("bang-dudb-test", "cards/test")
		assert(objects.objectSummaries.size == 1)
		s3Client.deleteObject("bang-dudb-test", "cards/test")
	}
}
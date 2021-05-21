package it.zampa.bangdudb

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import it.zampa.bangdudb.delivery.S3ImageUploader
import it.zampa.bangdudb.domain.ImageUploader
import it.zampa.bangdudb.domain.usecase.AddCardUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfig {

	val s3Client: AmazonS3 = AmazonS3ClientBuilder.standard()
		.withRegion(Regions.EU_WEST_1)
		.withCredentials(ProfileCredentialsProvider("bang-dudb"))
		.build()
	val imageUploader: ImageUploader = S3ImageUploader(s3Client)

	@Bean
	fun addCardUseCase(): AddCardUseCase {
		return AddCardUseCase(imageUploader)
	}

}
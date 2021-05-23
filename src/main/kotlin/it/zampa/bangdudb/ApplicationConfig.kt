package it.zampa.bangdudb

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import it.zampa.bangdudb.delivery.S3ImageUploader
import it.zampa.bangdudb.domain.ImageUploader
import it.zampa.bangdudb.domain.usecase.AddBannerUseCase
import it.zampa.bangdudb.domain.usecase.AddCardUseCase
import it.zampa.bangdudb.repository.BannerRepository
import it.zampa.bangdudb.repository.CardRepository
import it.zampa.bangdudb.utils.ImageIOImageCompressionService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service

@Configuration
@Service
class ApplicationConfig(val cardRepository: CardRepository, val bannerRepository: BannerRepository) {

	val s3Client: AmazonS3 = AmazonS3ClientBuilder.standard()
		.withRegion(Regions.EU_WEST_1)
		.withCredentials(ProfileCredentialsProvider("bang-dudb"))
		.build()
	val imageUploader: ImageUploader = S3ImageUploader(s3Client)
	val imageCompressionService = ImageIOImageCompressionService()

	@Bean
	fun addCardUseCase(): AddCardUseCase =
		AddCardUseCase(imageUploader, cardRepository, imageCompressionService)

	@Bean
	fun addBannerUseCase(): AddBannerUseCase =
		AddBannerUseCase(imageUploader, bannerRepository, imageCompressionService)

}
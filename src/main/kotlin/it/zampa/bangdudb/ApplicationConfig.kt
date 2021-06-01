package it.zampa.bangdudb

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import it.zampa.bangdudb.delivery.repository.DbBannerRepository
import it.zampa.bangdudb.delivery.repository.DbCardRepository
import it.zampa.bangdudb.delivery.repository.DbEventRepository
import it.zampa.bangdudb.delivery.service.ImageIOImageCompressionService
import it.zampa.bangdudb.delivery.service.S3ImageUploader
import it.zampa.bangdudb.domain.repository.BannerRepository
import it.zampa.bangdudb.domain.repository.CardRepository
import it.zampa.bangdudb.domain.repository.EventRepository
import it.zampa.bangdudb.domain.service.ImageCompressionService
import it.zampa.bangdudb.domain.service.ImageUploader
import it.zampa.bangdudb.domain.usecase.AddBannerUseCase
import it.zampa.bangdudb.domain.usecase.AddCardUseCase
import it.zampa.bangdudb.domain.usecase.AddEventUseCase
import it.zampa.bangdudb.domain.usecase.SearchEventUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Configuration
@Service
class ApplicationConfig {

	@Autowired
	private lateinit var dataSource: DataSource

	@Bean
	@Primary
	fun namedParameterJdbcTemplate(): NamedParameterJdbcTemplate {
		return NamedParameterJdbcTemplate(dataSource)
	}

	@Bean
	fun s3Client(): AmazonS3 = AmazonS3ClientBuilder.standard()
		.withRegion(Regions.EU_WEST_1)
		.withCredentials(ProfileCredentialsProvider("bang-dudb"))
		.build()

	@Bean
	fun imageUploader(
		s3Client: AmazonS3
	): ImageUploader = S3ImageUploader(s3Client)

	@Bean
	fun imageCompressionService() =
		ImageIOImageCompressionService()

	@Bean
	fun cardRepository(namedParameterJdbcTemplate: NamedParameterJdbcTemplate) =
		DbCardRepository(namedParameterJdbcTemplate)

	@Bean
	fun eventRepository(namedParameterJdbcTemplate: NamedParameterJdbcTemplate) =
		DbEventRepository(namedParameterJdbcTemplate)

	@Bean
	fun bannerRepository(namedParameterJdbcTemplate: NamedParameterJdbcTemplate) =
		DbBannerRepository(namedParameterJdbcTemplate)

	@Bean
	fun addCardUseCase(
		cardRepository: CardRepository,
		imageUploader: ImageUploader,
		imageCompressionService: ImageCompressionService,
	): AddCardUseCase =
		AddCardUseCase(imageUploader, cardRepository, imageCompressionService)

	@Bean
	fun addBannerUseCase(
		bannerRepository: BannerRepository,
		imageUploader: ImageUploader,
		imageCompressionService: ImageCompressionService,
	): AddBannerUseCase =
		AddBannerUseCase(imageUploader, bannerRepository, imageCompressionService)

	@Bean
	fun addEventUseCase(
		eventRepository: EventRepository,
		imageUploader: ImageUploader,
		imageCompressionService: ImageCompressionService,
	): AddEventUseCase =
		AddEventUseCase(imageUploader, eventRepository, imageCompressionService)

	@Bean
	fun searchEventUseCase(
		cardRepository: CardRepository,
		eventRepository: EventRepository,
	): SearchEventUseCase =
		SearchEventUseCase(cardRepository, eventRepository)

}
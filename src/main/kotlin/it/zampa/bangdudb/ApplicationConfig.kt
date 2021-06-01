package it.zampa.bangdudb

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import it.zampa.bangdudb.delivery.repository.DbBannerRepository
import it.zampa.bangdudb.delivery.repository.DbCardRepository
import it.zampa.bangdudb.delivery.repository.DbEventRepository
import it.zampa.bangdudb.delivery.service.S3ImageUploader
import it.zampa.bangdudb.domain.repository.BannerRepository
import it.zampa.bangdudb.domain.repository.CardRepository
import it.zampa.bangdudb.domain.repository.EventRepository
import it.zampa.bangdudb.domain.service.ImageUploader
import it.zampa.bangdudb.domain.usecase.AddBannerUseCase
import it.zampa.bangdudb.domain.usecase.AddCardUseCase
import it.zampa.bangdudb.domain.usecase.AddEventUseCase
import it.zampa.bangdudb.utils.ImageIOImageCompressionService
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

	val s3Client: AmazonS3 = AmazonS3ClientBuilder.standard()
		.withRegion(Regions.EU_WEST_1)
		.withCredentials(ProfileCredentialsProvider("bang-dudb"))
		.build()
	val imageUploader: ImageUploader = S3ImageUploader(s3Client)
	val imageCompressionService = ImageIOImageCompressionService()

	@Bean
	fun cardRepository(namedParameterJdbcTemplate: NamedParameterJdbcTemplate) = DbCardRepository(namedParameterJdbcTemplate)

	@Bean
	fun eventRepository(namedParameterJdbcTemplate: NamedParameterJdbcTemplate) = DbEventRepository(namedParameterJdbcTemplate)

	@Bean
	fun bannerRepository(namedParameterJdbcTemplate: NamedParameterJdbcTemplate) = DbBannerRepository(namedParameterJdbcTemplate)

	@Bean
	fun addCardUseCase(
		cardRepository: CardRepository
	): AddCardUseCase =
		AddCardUseCase(imageUploader, cardRepository, imageCompressionService)

	@Bean
	fun addBannerUseCase(
		bannerRepository: BannerRepository
	): AddBannerUseCase =
		AddBannerUseCase(imageUploader, bannerRepository, imageCompressionService)

	@Bean
	fun addEventUseCase(
		eventRepository: EventRepository
	): AddEventUseCase =
		AddEventUseCase(imageUploader, eventRepository, imageCompressionService)

}
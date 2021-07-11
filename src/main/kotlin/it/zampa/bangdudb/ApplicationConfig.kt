package it.zampa.bangdudb

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import it.zampa.bangdudb.delivery.repository.DbBannerRepository
import it.zampa.bangdudb.delivery.repository.DbCardRepository
import it.zampa.bangdudb.delivery.repository.DbEventRepository
import it.zampa.bangdudb.delivery.repository.DbSongRepository
import it.zampa.bangdudb.delivery.service.ImageIOImageCompressionService
import it.zampa.bangdudb.delivery.service.S3ImageUploader
import it.zampa.bangdudb.domain.repository.BannerRepository
import it.zampa.bangdudb.domain.repository.CardRepository
import it.zampa.bangdudb.domain.repository.EventRepository
import it.zampa.bangdudb.domain.repository.SongRepository
import it.zampa.bangdudb.domain.service.ImageCompressionService
import it.zampa.bangdudb.domain.usecase.*
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

	private val bucketName = "bang-dudb-test"
	private val bucketUrl = "https://bang-dudb-test.s3-eu-west-1.amazonaws.com/"
	private val cardsDirectory = "cards"
	private val eventsDirectory = "events"
	private val bannersDirectory = "banners"
	private val songsDirectory = "songs"

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
	fun songRepository(namedParameterJdbcTemplate: NamedParameterJdbcTemplate) =
		DbSongRepository(namedParameterJdbcTemplate)

	@Bean
	fun addCardUseCase(
		cardRepository: CardRepository,
		s3Client: AmazonS3,
		imageCompressionService: ImageCompressionService,
	): AddCardUseCase =
		AddCardUseCase(
			S3ImageUploader(s3Client, bucketName, bucketUrl, cardsDirectory),
			cardRepository,
			imageCompressionService
		)

	@Bean
	fun addBannerUseCase(
		bannerRepository: BannerRepository,
		s3Client: AmazonS3,
		imageCompressionService: ImageCompressionService,
	): AddBannerUseCase =
		AddBannerUseCase(
			S3ImageUploader(s3Client, bucketName, bucketUrl, bannersDirectory),
			bannerRepository
		)

	@Bean
	fun addEventUseCase(
		eventRepository: EventRepository,
		s3Client: AmazonS3,
		imageCompressionService: ImageCompressionService,
	): AddEventUseCase =
		AddEventUseCase(
			S3ImageUploader(s3Client, bucketName, bucketUrl, eventsDirectory),
			eventRepository
		)

	@Bean
	fun addSongUseCase(
		songRepository: SongRepository,
		s3Client: AmazonS3
	): AddSongUseCase =
		AddSongUseCase(
			S3ImageUploader(s3Client, bucketName, bucketUrl, songsDirectory),
			songRepository
		)

	@Bean
	fun editCardUseCase(
		cardRepository: CardRepository
	): EditCardUseCase =
		EditCardUseCase(cardRepository)

	@Bean
	fun editEventUseCase(
		eventRepository: EventRepository
	): EditEventUseCase =
		EditEventUseCase(eventRepository)

	@Bean
	fun searchEventUseCase(
		cardRepository: CardRepository,
		eventRepository: EventRepository,
	): SearchEventUseCase =
		SearchEventUseCase(cardRepository, eventRepository)

	@Bean
	fun searchBannerUseCase(
		cardRepository: CardRepository,
		bannerRepository: BannerRepository,
	): SearchBannerUseCase =
		SearchBannerUseCase(cardRepository, bannerRepository)

}
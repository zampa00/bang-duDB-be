package it.zampa.bangdudb.domain.usecase

import it.zampa.bangdudb.delivery.datamodel.`in`.InputCard
import it.zampa.bangdudb.domain.Card
import it.zampa.bangdudb.domain.repository.CardRepository
import it.zampa.bangdudb.domain.service.ImageCompressionService
import it.zampa.bangdudb.domain.service.ImageUploader
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.InputStream
import java.time.LocalDate

class AddCardUseCaseTest {

	val imageUploader = mock<ImageUploader>()
	val cardRepository = mock<CardRepository>()
	val imageCompressionService = mock<ImageCompressionService>()
	val usecase = AddCardUseCase(imageUploader, cardRepository, imageCompressionService)

	val firstImageToUpload = mock<MultipartFile>()
	val firstImageToUploadName = "firstImageName"
	val firstImageInputStream = mock<InputStream>()

	val secondImageToUpload = mock<MultipartFile>()
	val secondImageToUploadName = "secondImageName"
	val secondImageInputStream = mock<InputStream>()

	val avatarImageToUpload = mock<MultipartFile>()
	val avatarImageToUploadName = "avatarImageName"
	val avatarImageInputStream = mock<InputStream>()

	val firstCompressedFile = mock<File>()
	val secondCompressedFile = mock<File>()


	@BeforeEach
	fun setUp() {
		whenever(firstImageToUpload.resource).thenReturn(mock())
		whenever(firstImageToUpload.inputStream).thenReturn(firstImageInputStream)
		whenever(firstImageToUpload.resource.filename).thenReturn(firstImageToUploadName)

		whenever(secondImageToUpload.resource).thenReturn(mock())
		whenever(secondImageToUpload.inputStream).thenReturn(secondImageInputStream)
		whenever(secondImageToUpload.resource.filename).thenReturn(secondImageToUploadName)

		whenever(avatarImageToUpload.resource).thenReturn(mock())
		whenever(avatarImageToUpload.inputStream).thenReturn(avatarImageInputStream)
		whenever(avatarImageToUpload.resource.filename).thenReturn(avatarImageToUploadName)

		whenever(imageUploader.upload(firstImageInputStream, firstImageToUploadName)).thenReturn("${baseCardUrl}001_0001_1.png")
		whenever(imageUploader.upload(secondImageInputStream, secondImageToUploadName)).thenReturn("${baseCardUrl}001_0001_2.png")
		whenever(imageUploader.upload(avatarImageInputStream, avatarImageToUploadName)).thenReturn("${baseCardUrl}avatar.png")
		whenever(imageUploader.upload(firstCompressedFile, "001_0001_1_lq.jpg")).thenReturn("${baseCardUrl}001_0001_1_lq.jpg")
		whenever(imageUploader.upload(secondCompressedFile, "001_0001_2_lq.jpg")).thenReturn("${baseCardUrl}001_0001_2_lq.jpg")

		whenever(imageCompressionService.compress(eq(firstImageInputStream), any())).thenReturn(firstCompressedFile)
		whenever(imageCompressionService.compress(eq(secondImageInputStream), any())).thenReturn(secondCompressedFile)

		whenever(firstCompressedFile.name).thenReturn("001_0001_1_lq.jpg")
		whenever(secondCompressedFile.name).thenReturn("001_0001_2_lq.jpg")
	}

	@Test
	fun `should call the upload service on the first image`() {
		usecase.execute(card, firstImageToUpload, secondImageToUpload, avatarImageToUpload)

		verify(imageUploader).upload(firstImageInputStream, firstImageToUploadName)
	}

	@Test
	fun `should call the upload service on the second image`() {
		usecase.execute(card, firstImageToUpload, secondImageToUpload, avatarImageToUpload)

		verify(imageUploader).upload(secondImageInputStream, secondImageToUploadName)
	}

	@Test
	fun `should resize the images`() {
		usecase.execute(card, firstImageToUpload, secondImageToUpload, avatarImageToUpload)

		verify(imageCompressionService, times(2)).compress(any(), any())
	}

	@Test
	fun `should upload 5 images in total`() {
		usecase.execute(card, firstImageToUpload, secondImageToUpload, avatarImageToUpload)

		verify(imageUploader, times(2)).upload(any<File>(), any())
		verify(imageUploader, times(3)).upload(any<InputStream>(), any())
	}

	@Test
	fun `should save the card on db`() {
		usecase.execute(card, firstImageToUpload, secondImageToUpload, avatarImageToUpload)

		verify(cardRepository).save(dbCard)
	}

	companion object {
		val baseCardUrl = "https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/"

		val card = InputCard(
			id = "014_0001",
			banner_id = 1,
			event_id = 1,

			character = "Nanahoshi Ren",
			band = "Argonavis",
			cardName = "card name",
			cardNameJp = "card name jp",
			rarity = 4,
			attribute = "cool",

			releaseDate = "2021-01-21T21:02:22.523+02:00",
			power = 1,
			pf = 1,
			tec = 1,

			vi = 1,
			skillSessionName = "skill session name",
			skillSessionNameJp = "skill session name jp",
			skillSessionDescription = "skill session description",
			skillSessionDescriptionJp = "skill session description jp",
			skilltype = "scorer",
			skillDailylifeName = "skill daily life name",
			skillDailylifeNameJp = "skill daily life name jp",
			skillDailylifeDescription = "skill daily life description",
			skillDailylifeDescriptionJp = "skill daily life description jp",

			isGacha = false,
			isUnavailableGacha = false,
			isEvent = false,
			isBirthday = false,
			isPromo = false
		)

		val dbCard = Card(
			id = "014_0001",
			banner_id = 1,
			event_id = 1,
			character_name = "Nanahoshi Ren",
			band = "Argonavis",
			card_name = "card name",
			card_name_jp = "card name jp",
			rarity = 4,
			attribute = "cool",
			release_date = LocalDate.parse("2021-01-21")!!,
			power = 1,
			pf = 1,
			tec = 1,
			vi = 1,
			skill_session_name = "skill session name",
			skill_session_name_jp = "skill session name jp",
			skill_session_description = "skill session description",
			skill_session_description_jp = "skill session description jp",
			skill_session_type = "scorer",
			skill_dailylife_name = "skill daily life name",
			skill_dailylife_name_jp = "skill daily life name jp",
			skill_dailylife_description = "skill daily life description",
			skill_dailylife_description_jp = "skill daily life description jp",
			is_gacha = false,
			is_unavailable_gacha = false,
			is_event = false,
			is_birthday = false,
			is_promo = false,
			src_base_lq = "${baseCardUrl}001_0001_1_lq.jpg",
			src_idl_lq = "${baseCardUrl}001_0001_2_lq.jpg",
			src_base_hq = "${baseCardUrl}001_0001_1.png",
			src_idl_hq = "${baseCardUrl}001_0001_2.png",
			src_avatar = "${baseCardUrl}avatar.png"
		)
	}

}
package it.zampa.bangdudb.domain.usecase

import it.zampa.bangdudb.delivery.data.InputCard
import it.zampa.bangdudb.domain.ImageUploader
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.web.multipart.MultipartFile

class AddCardUseCaseTest {

	val imageUploader = mock<ImageUploader>()
	val usecase = AddCardUseCase(imageUploader)

	@Test
	fun `should call the upload service`() {
		val firstImageToUpload = mock<MultipartFile>();
		val imageName = "imageName"
		whenever(firstImageToUpload.resource).thenReturn(mock())
		whenever(firstImageToUpload.resource.file).thenReturn(mock())
		whenever(firstImageToUpload.resource.filename).thenReturn(imageName)

		usecase.execute(card, firstImageToUpload, firstImageToUpload)

		verify(imageUploader).uploadCard(firstImageToUpload, imageName)
	}

	companion object {
		val card = InputCard(
			id = "001_0001",

			characters = "Nanahoshi Ren",
			band = "Argonavis",
			cardName = "card name",
			rarities = "4*",
			attributes = "cool",
			releaseDate = "2021-01-21",

			power = 1,
			pf = 1,
			tec = 1,
			vi = 1,

			skillSessionName = "skill session name",
			skillSessionDescription = "skill session description",
			skilltype = "scorer",
			skillDailylifeName = "skill daily life name",
			skillDailylifeDescription = "skill daily life description",

			isGacha = false,
			isUnavailableGacha = false,
			isEvent = false,
			isBirthday = false,
			isPromo = false
		)
	}

}
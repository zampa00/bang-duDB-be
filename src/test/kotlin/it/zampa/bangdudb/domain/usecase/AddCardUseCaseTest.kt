package it.zampa.bangdudb.domain.usecase

import it.zampa.bangdudb.domain.ImageUploader
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.springframework.web.multipart.MultipartFile

class AddCardUseCaseTest {

	val imageUploader = mock<ImageUploader>()
	val usecase = AddCardUseCase(imageUploader)

	@Test
	fun `should call the upload service`() {
		val firstImageToUpload = mock<MultipartFile>();

		usecase.execute("", firstImageToUpload, firstImageToUpload)

		verify(imageUploader).uploadCard(firstImageToUpload)
	}

}
package it.zampa.bangdudb.domain.usecase

import it.zampa.bangdudb.domain.ImageUploader
import org.springframework.web.multipart.MultipartFile

class AddCardUseCase(val imageUploader: ImageUploader) {
	fun execute(cardDetails: String, imgBase: MultipartFile, imgIdl: MultipartFile) {
		println("sono nello usecase!")
		imageUploader.uploadCard(imgBase)
//		s3Client.putObject(
//			"",
//			"",
//			imgBase.resource.file
//		)
	}
}
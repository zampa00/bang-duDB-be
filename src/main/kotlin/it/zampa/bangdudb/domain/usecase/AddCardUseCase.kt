package it.zampa.bangdudb.domain.usecase

import it.zampa.bangdudb.delivery.data.InputCard
import it.zampa.bangdudb.domain.ImageUploader
import org.springframework.web.multipart.MultipartFile

class AddCardUseCase(val imageUploader: ImageUploader) {
	fun execute(cardDetails: InputCard, imgBase: MultipartFile, imgIdl: MultipartFile) {
		println("sono nello usecase!")

		imageUploader.uploadCard(imgBase, imgBase.resource.filename!!)
//		s3Client.putObject(
//			"",
//			"",
//			imgBase.resource.file
//		)
	}
}
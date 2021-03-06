package it.zampa.bangdudb.domain.usecase

import it.zampa.bangdudb.delivery.datamodel.`in`.InputBanner
import it.zampa.bangdudb.domain.Banner
import it.zampa.bangdudb.domain.repository.BannerRepository
import it.zampa.bangdudb.domain.service.ImageUploader
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

class AddBannerUseCase(
	val imageUploader: ImageUploader,
	val bannerRepository: BannerRepository
) {

	var logger: Logger = LoggerFactory.getLogger(this::class.java)

	fun execute(bannerData: InputBanner, bannerImage: MultipartFile) {
		logger.info("AddBannerUseCase start")

		val imgHqUrl = imageUploader.upload(bannerImage.inputStream, bannerImage.resource.filename!!)

		logger.info("all banner's image uploaded")

		val bannerToSave: Banner = bannerData.mapToDomain(imgHqUrl)

		logger.info("mapper banned to domain: $bannerToSave")

		bannerRepository.save(bannerToSave)

		logger.info("banner saved")
	}

}

private fun InputBanner.mapToDomain(imgHqUrl: String): Banner {
	return Banner(
		name = this.name,
		name_jp = this.nameJp,
		description = this.description,
		description_jp = this.descriptionJp,
		start_date = LocalDate.parse(this.startDate)!!,
		end_date = LocalDate.parse(this.endDate)!!,
		image_hq = imgHqUrl
	)
}

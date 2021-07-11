package it.zampa.bangdudb.domain.usecase

import it.zampa.bangdudb.delivery.datamodel.`in`.InputBanner
import it.zampa.bangdudb.domain.Banner
import it.zampa.bangdudb.domain.repository.BannerRepository
import java.time.LocalDate

class EditBannerUseCase(
	private val bannerRepository: BannerRepository
) {
	fun execute(banner: InputBanner) {
		val oldBanner = bannerRepository.findById(banner.id!!)!!
		val newBanner = oldBanner.overrideWith(banner)
		bannerRepository.editBanner(newBanner)
	}

}

private fun Banner.overrideWith(inputBanner: InputBanner): Banner = Banner(
	id = this.id,
	name = inputBanner.name,
	name_jp = inputBanner.nameJp,
	description = inputBanner.description,
	description_jp = inputBanner.descriptionJp,
	start_date = LocalDate.parse(inputBanner.startDate),
	end_date = LocalDate.parse(inputBanner.endDate),
	image_hq = this.image_hq
)

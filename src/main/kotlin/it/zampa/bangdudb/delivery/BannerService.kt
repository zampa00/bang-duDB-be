package it.zampa.bangdudb.delivery

import it.zampa.bangdudb.domain.Banner
import it.zampa.bangdudb.repository.BannerRepository
import org.springframework.stereotype.Service

@Service
class BannerService(val db: BannerRepository) {

	fun findBanners(): List<Banner> = db.findAllBanners()

	fun findBanner(bannerId: Int): Banner? = db.findBanner(bannerId)

}
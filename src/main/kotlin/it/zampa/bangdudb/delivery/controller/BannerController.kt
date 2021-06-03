package it.zampa.bangdudb.delivery.controller

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import it.zampa.bangdudb.delivery.datamodel.`in`.InputBanner
import it.zampa.bangdudb.delivery.datamodel.out.BannerSummary
import it.zampa.bangdudb.delivery.datamodel.out.BannerWithCards
import it.zampa.bangdudb.delivery.datamodel.out.ListItem
import it.zampa.bangdudb.delivery.datamodel.out.Paginated
import it.zampa.bangdudb.domain.repository.BannerRepository
import it.zampa.bangdudb.domain.usecase.AddBannerUseCase
import it.zampa.bangdudb.domain.usecase.SearchBannerUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@CrossOrigin
class BannerController(
	val bannerRepository: BannerRepository,
	val addBannerUseCase: AddBannerUseCase,
	val searchBannerUseCase: SearchBannerUseCase
) {

	var logger: Logger = LoggerFactory.getLogger(this::class.java)

	private val mapper = ObjectMapper()
		.registerModule(KotlinModule())
		.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

	@GetMapping("/banners")
	fun getBanners(@RequestParam page: Int, @RequestParam size: Int): Paginated<BannerSummary> =
		bannerRepository.findBannersPaginated(page - 1, size)

	@GetMapping("/banner/{bannerId}")
	@ResponseBody
	fun getBannerFromId(@PathVariable bannerId: Int): BannerWithCards =
		searchBannerUseCase.search(bannerId)

	@GetMapping("/bannersList")
	@ResponseBody
	fun getBannersList(): List<ListItem> =
		searchBannerUseCase.getAllBannersIds()

	@PostMapping("/addBanner")
	fun addBanner(@RequestParam bannerData: String, @RequestParam bannerImage: MultipartFile): ResponseEntity<String> {
		logger.info("received $bannerData")
		logger.info("received image $bannerImage")

		val banner = mapper.readValue(bannerData, InputBanner::class.java)

		logger.info("mapped to $banner")

		addBannerUseCase.execute(banner, bannerImage)
		return ResponseEntity<String>(HttpStatus.OK)
	}

}
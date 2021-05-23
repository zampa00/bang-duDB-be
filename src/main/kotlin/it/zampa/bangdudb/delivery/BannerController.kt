package it.zampa.bangdudb.delivery

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import it.zampa.bangdudb.delivery.data.InputBanner
import it.zampa.bangdudb.domain.Banner
import it.zampa.bangdudb.domain.usecase.AddBannerUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@CrossOrigin
class BannerController(val service: BannerService, val addBannerUseCase: AddBannerUseCase) {

	var logger: Logger = LoggerFactory.getLogger(this::class.java)

	private val mapper = ObjectMapper()
		.registerModule(KotlinModule())
		.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

	@GetMapping("/banners")
	fun getBanner(): List<Banner> {
		return service.findBanners()
	}

	@GetMapping("/banner/{bannerId}")
	@ResponseBody
	fun getBannerFromId(@PathVariable bannerId: String): Banner? {
		return service.findBanner(bannerId)
	}

	@PostMapping("/addBanner")
	fun test(@RequestParam bannerData: String, @RequestParam bannerImage: MultipartFile): ResponseEntity<String> {
		logger.info("received $bannerData")
		logger.info("received image $bannerImage")

		val banner = mapper.readValue(bannerData, InputBanner::class.java)

		logger.info("mapped to $banner")

		addBannerUseCase.execute(banner, bannerImage)
		return ResponseEntity<String>(HttpStatus.OK)
	}

}
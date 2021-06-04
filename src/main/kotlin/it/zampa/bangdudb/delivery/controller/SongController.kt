package it.zampa.bangdudb.delivery.controller

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import it.zampa.bangdudb.delivery.datamodel.`in`.InputSong
import it.zampa.bangdudb.delivery.datamodel.out.Paginated
import it.zampa.bangdudb.domain.Song
import it.zampa.bangdudb.domain.repository.SongRepository
import it.zampa.bangdudb.domain.usecase.AddSongUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@CrossOrigin
class SongController(val songRepository: SongRepository, val addSongUseCase: AddSongUseCase) {

	var logger: Logger = LoggerFactory.getLogger(this::class.java)

	private val mapper = ObjectMapper()
		.registerModule(KotlinModule())
		.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

	@GetMapping("/songs")
	fun getCards(@RequestParam page: Int, @RequestParam size: Int): Paginated<Song> =
		songRepository.findSongsPaginated(page - 1, size)

	@GetMapping("/searchSongs")
	@ResponseBody
	fun getCardsFrom(
		@RequestParam(name = "bands") bands: List<String>?,
		@RequestParam(name = "isCover") is_cover: Boolean?,
		@RequestParam page: Int,
		@RequestParam size: Int
	): Paginated<Song> {
		return songRepository.findSongsPaginatedFilteredBy(page - 1, size,
			bands, is_cover
		)
	}

	@PostMapping("/addSong")
	fun addBanner(@RequestParam songData: String, @RequestParam songImage: MultipartFile): ResponseEntity<String> {
		logger.info("received $songData")
		logger.info("received image $songImage")

		val song = mapper.readValue(songData, InputSong::class.java)

		logger.info("mapped to $song")

		addSongUseCase.execute(song, songImage)

		return ResponseEntity<String>(HttpStatus.OK)
	}

}
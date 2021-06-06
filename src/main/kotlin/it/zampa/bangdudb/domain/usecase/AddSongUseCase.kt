package it.zampa.bangdudb.domain.usecase

import it.zampa.bangdudb.delivery.datamodel.`in`.InputSong
import it.zampa.bangdudb.domain.Song
import it.zampa.bangdudb.domain.repository.SongRepository
import it.zampa.bangdudb.domain.service.ImageUploader
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

class AddSongUseCase(
	val imageUploader: ImageUploader,
	val songRepository: SongRepository
) {

	var logger: Logger = LoggerFactory.getLogger(this::class.java)

	fun execute(songData: InputSong, songImage: MultipartFile) {
		logger.info("AddSongUseCase start")

		val imgHqUrl = imageUploader.upload(songImage.inputStream, songImage.resource.filename!!)

		logger.info("all song's image uploaded")

		val songToSave: Song = songData.mapToDomain(imgHqUrl)

		logger.info("mapper song to domain: $songToSave")

		songRepository.save(songToSave)

		logger.info("song saved")
	}

}

private fun InputSong.mapToDomain(imgHqUrl: String): Song {
	return Song(
		name = this.name,
		name_jp = this.name_jp,
		band = this.band,
		lyricist = this.lyricist,
		composer = this.composer,
		arranger = this.arranger,
		difficulty = this.difficulty,
		other_info = this.other_info,
		release_date = LocalDate.parse(this.release_date)!!,
		is_cover = this.song_type == "cover",
		image = imgHqUrl
	)
}

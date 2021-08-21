package it.zampa.bangdudb.domain.usecase

import it.zampa.bangdudb.delivery.datamodel.`in`.InputSong
import it.zampa.bangdudb.domain.Song
import it.zampa.bangdudb.domain.repository.SongRepository
import java.time.LocalDate

class EditSongUseCase(
	private val songRepository: SongRepository
) {

	fun execute(song: InputSong) {
		val oldSong = songRepository.findById(song.id!!)!!
		val newSong = oldSong.overrideWith(song);
		songRepository.editSong(newSong)
	}

}

private fun Song.overrideWith(newSong: InputSong): Song = Song(
	id = newSong.id,
	name = newSong.name,
	name_jp = newSong.name_jp,
	band = newSong.band,
	lyricist = newSong.lyricist,
	composer = newSong.composer,
	arranger = newSong.arranger,
	difficulty = newSong.difficulty,
	other_info = newSong.other_info,
	is_cover = newSong.song_type == "cover",
	release_date = LocalDate.parse(newSong.release_date),
	image = image
)

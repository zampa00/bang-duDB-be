package it.zampa.bangdudb.domain.usecase

import it.zampa.bangdudb.delivery.datamodel.`in`.InputSong
import it.zampa.bangdudb.domain.repository.SongRepository

class EditSongUseCase(
	val songRepository: SongRepository
) {

	fun execute(song: InputSong) {
		TODO("Not yet implemented")
	}

}

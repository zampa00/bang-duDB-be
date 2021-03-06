package it.zampa.bangdudb.domain.repository

import it.zampa.bangdudb.delivery.datamodel.out.Paginated
import it.zampa.bangdudb.delivery.datamodel.out.SongSummary
import it.zampa.bangdudb.domain.Song

interface SongRepository {
	fun save(song: Song)
	fun findById(songId: Int): Song?
	fun findSongsPaginated(page: Int, resultsPerPage: Int): Paginated<SongSummary>
	fun findSongsPaginatedFilteredBy(
		page: Int,
		resultsPerPage: Int,
		bands: List<String>? = null,
		is_cover: Boolean? = null,
	): Paginated<SongSummary>

	fun editSong(copy: Song)
}
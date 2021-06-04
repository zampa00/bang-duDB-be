package it.zampa.bangdudb.delivery.repository

import it.zampa.bangdudb.delivery.datamodel.out.Paginated
import it.zampa.bangdudb.domain.Song
import it.zampa.bangdudb.domain.repository.SongRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class DbSongRepository(val jdbcTemplate: NamedParameterJdbcTemplate) : SongRepository {

	val TABLE_NAME: String = "songs"

	override fun findSongsPaginated(page: Int, resultsPerPage: Int): Paginated<Song> =
		try {
			Paginated(
				summary = jdbcTemplate.queryForList(
					"SELECT * FROM $TABLE_NAME LIMIT :resultsPerPage OFFSET :offset",
					MapSqlParameterSource()
						.addValue("offset", page * resultsPerPage)
						.addValue("resultsPerPage", resultsPerPage)
				).map {
					mapToSong(it)
				},
				totalPages = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM $TABLE_NAME", MapSqlParameterSource(), Integer::class.java) as Int
			)

		} catch (ex: EmptyResultDataAccessException) {
			Paginated(emptyList(), 0)
		}

	override fun findSongsPaginatedFilteredBy(
		page: Int,
		resultsPerPage: Int,
		bands: List<String>?,
		is_cover: Boolean?
	): Paginated<Song> = try {
		val sqlParameterSource = MapSqlParameterSource()
			.addValue("bands", bands)
			.addValue("is_cover", is_cover)
			.addValue("offset", page * resultsPerPage)
			.addValue("resultsPerPage", resultsPerPage)
		val whereClause = "WHERE ((:bands::text) is null OR band in (:bands::text)) " +
			"AND (:is_cover::boolean is null OR is_cover = :is_cover::boolean) "
		Paginated(
			summary = jdbcTemplate.queryForList(
				"SELECT * FROM $TABLE_NAME " +
					whereClause +
					"LIMIT :resultsPerPage OFFSET :offset",
				sqlParameterSource
			).map {
				mapToSong(it)
			},
			totalPages = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM $TABLE_NAME $whereClause", sqlParameterSource, Integer::class.java) as Int
		)

	} catch (ex: EmptyResultDataAccessException) {
		Paginated(emptyList(), 0)
	}

	override fun save(song: Song) {
		jdbcTemplate.update("INSERT INTO $TABLE_NAME" +
			"( " +
			"name, " +
			"name_jp, " +
			"band, " +
			"is_cover, " +
			"release_date, " +
			"image " +
			")" +
			" VALUES( " +
			":name, " +
			":name_jp, " +
			":band, " +
			":is_cover, " +
			":release_date, " +
			":image " +
			")",
			MapSqlParameterSource()
				.addValue("name", song.name)
				.addValue("name_jp", song.name_jp)
				.addValue("band", song.band)
				.addValue("is_cover", song.is_cover)
				.addValue("release_date", song.release_date)
				.addValue("image", song.image)
		)
	}

	private fun mapToSong(it: MutableMap<String, Any>) = Song(
		name = it["name"] as String,
		name_jp = it["name_jp"] as String,
		band = it["band"] as String,
		is_cover = it["is_cover"] as Boolean,
		release_date = (it["release_date"] as java.sql.Date).toLocalDate(),
		image = it["image"] as String,
	)

}

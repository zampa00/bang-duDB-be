package it.zampa.bangdudb.delivery.repository

import it.zampa.bangdudb.delivery.datamodel.out.BannerSummary
import it.zampa.bangdudb.delivery.datamodel.out.ListItem
import it.zampa.bangdudb.delivery.datamodel.out.Paginated
import it.zampa.bangdudb.domain.Banner
import it.zampa.bangdudb.domain.repository.BannerRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class DbBannerRepository(val jdbcTemplate: NamedParameterJdbcTemplate) : BannerRepository {

	val TABLE_NAME: String = "banners"
	val ORDER_QUERY: String = "ORDER BY start_date DESC"

	override fun findById(bannerId: Int): Banner? =
		try {
			jdbcTemplate.queryForObject(
				"SELECT * FROM $TABLE_NAME WHERE id = :bannerId",
				MapSqlParameterSource()
					.addValue("bannerId", bannerId)
			) { resultSet, _ ->
				Banner(
					id = resultSet.getInt("id"),
					name = resultSet.getString("name"),
					name_jp = resultSet.getString("name_jp"),
					description = resultSet.getString("description"),
					description_jp = resultSet.getString("description_jp"),
					start_date = resultSet.getDate("start_date").toLocalDate(),
					end_date = resultSet.getDate("end_date").toLocalDate(),
					image_hq = resultSet.getString("image_hq"),
				)
			}
		} catch (ex: EmptyResultDataAccessException) {
			null
		}

	override fun findBannersForListing(): List<ListItem> =
		jdbcTemplate.queryForList(
			"SELECT * FROM $TABLE_NAME $ORDER_QUERY",
			MapSqlParameterSource()
		).map {
			mapToListItem(it)
		}

	override fun findBannersPaginated(page: Int, resultsPerPage: Int): Paginated<BannerSummary> =
		try {
			Paginated(
				summary = jdbcTemplate.queryForList(
					"SELECT * FROM $TABLE_NAME $ORDER_QUERY LIMIT :resultsPerPage OFFSET :offset",
					MapSqlParameterSource()
						.addValue("offset", page * resultsPerPage)
						.addValue("resultsPerPage", resultsPerPage)
				).map {
					mapToBannerSummary(it)
				},
				totalPages = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM $TABLE_NAME", MapSqlParameterSource(), Integer::class.java) as Int
			)

		} catch (ex: EmptyResultDataAccessException) {
			Paginated(emptyList(), 0)
		}

	override fun save(banner: Banner) {
		val insertQuery = "INSERT INTO $TABLE_NAME" +
			"( " +
			"name, " +
			"name_jp, " +
			"description, " +
			"description_jp, " +
			"start_date, " +
			"end_date, " +
			"image_hq" +
			")" +
			" VALUES( " +
			":name, " +
			":name_jp, " +
			":description, " +
			":description_jp, " +
			":start_date, " +
			":end_date, " +
			":image_hq" +
			")"
		val sqlParameterSource = MapSqlParameterSource()
			.addValue("name", banner.name)
			.addValue("name_jp", banner.name_jp)
			.addValue("description", banner.description)
			.addValue("description_jp", banner.description_jp)
			.addValue("start_date", banner.start_date)
			.addValue("end_date", banner.end_date)
			.addValue("image_hq", banner.image_hq)

		jdbcTemplate.update(insertQuery, sqlParameterSource)
	}

	override fun editBanner(banner: Banner) {
		val editQuery = "UPDATE $TABLE_NAME SET " +
			"name = :name, " +
			"name_jp = :name_jp, " +
			"description = :description, " +
			"description_jp = :description_jp, " +
			"start_date = :start_date, " +
			"end_date = :end_date " +
			" WHERE id = :id"
		val sqlParameterSource = MapSqlParameterSource()
			.addValue("id", banner.id)
			.addValue("name", banner.name)
			.addValue("name_jp", banner.name_jp)
			.addValue("description", banner.description)
			.addValue("description_jp", banner.description_jp)
			.addValue("start_date", banner.start_date)
			.addValue("end_date", banner.end_date)

		jdbcTemplate.update(editQuery, sqlParameterSource)
	}

	private fun mapToBannerSummary(it: Map<String, Any>) = BannerSummary(
		id = it["id"] as Int,
		name = it["name"] as String,
		name_jp = it["name_jp"] as String,
		image_hq = it["image_hq"] as String,
	)

	private fun mapToListItem(it: Map<String, Any>) = ListItem(
		id = it["id"] as Int,
		name = it["name"] as String
	)
}
package it.zampa.bangdudb.delivery.repository

import it.zampa.bangdudb.delivery.datamodel.out.EventSummary
import it.zampa.bangdudb.delivery.datamodel.out.Paginated
import it.zampa.bangdudb.domain.Event
import it.zampa.bangdudb.domain.repository.EventRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate


class DbEventRepository(val jdbcTemplate: NamedParameterJdbcTemplate) : EventRepository {

	val TABLE_NAME: String = "events"

	override fun findById(eventId: Int): Event? =
		try {
			jdbcTemplate.queryForObject(
				"SELECT * FROM $TABLE_NAME WHERE id = :eventId",
				MapSqlParameterSource()
					.addValue("eventId", eventId)
			) { resultSet, _ ->
				Event(
					id = resultSet.getInt("id"),
					name = resultSet.getString("name"),
					name_jp = resultSet.getString("name_jp"),
					description = resultSet.getString("description"),
					description_jp = resultSet.getString("description_jp"),
					start_date = resultSet.getDate("start_date").toLocalDate(),
					end_date = resultSet.getDate("end_date").toLocalDate(),
					image_hq = resultSet.getString("image_hq"),
					image_lq = resultSet.getString("image_lq"),
					stamp = resultSet.getString("stamp"),
					title_point = resultSet.getString("title_point"),
					title_rank = resultSet.getString("title_rank"),
					instrument = resultSet.getString("instrument"),
					accessory_point = resultSet.getString("accessory_point"),
					accessory_rank = resultSet.getString("accessory_rank"),
				)
			}
		} catch (ex: EmptyResultDataAccessException) {
			null
		}

	override fun findEventsPaginated(page: Int, resultsPerPage: Int): Paginated<EventSummary> =
		try {
			Paginated(
				summary = jdbcTemplate.queryForList(
					"SELECT * FROM $TABLE_NAME LIMIT :resultsPerPage OFFSET :offset",
					MapSqlParameterSource()
						.addValue("offset", page * resultsPerPage)
						.addValue("resultsPerPage", resultsPerPage)
				).map {
					mapToEventSummary(it)
				},
				totalPages = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM $TABLE_NAME", MapSqlParameterSource(), Integer::class.java) as Int
			)

		} catch (ex: EmptyResultDataAccessException) {
			Paginated(emptyList(), 0)
		}


	override fun save(event: Event) {
		val INSERT_QUERY = "INSERT INTO $TABLE_NAME" +
			"( " +
			"name, " +
			"name_jp, " +
			"description, " +
			"description_jp, " +
			"start_date, " +
			"end_date, " +
			"image_hq, " +
			"image_lq, " +
			"stamp, " +
			"title_point, " +
			"title_rank, " +
			"instrument, " +
			"accessory_point, " +
			"accessory_rank " +
			")" +
			" VALUES( " +
			":name, " +
			":name_jp, " +
			":description, " +
			":description_jp, " +
			":start_date, " +
			":end_date, " +
			":image_hq, " +
			":image_lq, " +
			":stamp, " +
			":title_point, " +
			":title_rank, " +
			":instrument, " +
			":accessory_point, " +
			":accessory_rank " +
			")"
		val sqlParameterSource = MapSqlParameterSource()
			.addValue("name", event.name)
			.addValue("name_jp", event.name_jp)
			.addValue("description", event.description)
			.addValue("description_jp", event.description_jp)
			.addValue("start_date", event.start_date)
			.addValue("end_date", event.end_date)
			.addValue("image_hq", event.image_hq)
			.addValue("image_lq", event.image_lq)
			.addValue("stamp", event.stamp)
			.addValue("title_point", event.title_point)
			.addValue("title_rank", event.title_rank)
			.addValue("instrument", event.instrument)
			.addValue("accessory_point", event.accessory_point)
			.addValue("accessory_rank", event.accessory_rank)

		jdbcTemplate.update(INSERT_QUERY, sqlParameterSource)
	}

	private fun mapToEventSummary(it: Map<String, Any>) = EventSummary(
		id = it["id"] as Int,
		name = it["name"] as String,
		name_jp = it["name_jp"] as String,
		image_lq = it["image_lq"] as String,
	)

}

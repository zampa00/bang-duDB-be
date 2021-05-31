package it.zampa.bangdudb.repository

import it.zampa.bangdudb.delivery.datamodel.CardSummary
import it.zampa.bangdudb.delivery.datamodel.PaginatedCards
import it.zampa.bangdudb.domain.Card
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class DbCardRepository(val jdbcTemplate: NamedParameterJdbcTemplate) {

	val TABLE_NAME: String = "cards"

	fun findById(cardId: String) =
		try {
			jdbcTemplate.queryForObject(
				"SELECT * FROM $TABLE_NAME WHERE card_id = :cardId",
				MapSqlParameterSource()
					.addValue("cardId", cardId)
			) { resultSet, _ ->
				Card(
					banner_id = resultSet.getInt("banner_id"),
					event_id = resultSet.getInt("event_id"),
					card_id = resultSet.getString("card_id"),
					character_name = resultSet.getString("character_name"),
					band = resultSet.getString("band"),
					card_name = resultSet.getString("card_name"),
					card_name_jp = resultSet.getString("card_name_jp"),
					rarity = resultSet.getInt("rarity"),
					attribute = resultSet.getString("attribute"),
					release_date = resultSet.getDate("release_date").toLocalDate(),
					power = resultSet.getInt("power"),
					pf = resultSet.getInt("pf"),
					tec = resultSet.getInt("tec"),
					vi = resultSet.getInt("vi"),
					skill_session_name = resultSet.getString("skill_session_name"),
					skill_session_name_jp = resultSet.getString("skill_session_name_jp"),
					skill_session_description = resultSet.getString("skill_session_description"),
					skill_session_description_jp = resultSet.getString("skill_session_description_jp"),
					skill_session_type = resultSet.getString("skill_session_type"),
					skill_dailylife_name = resultSet.getString("skill_dailylife_name"),
					skill_dailylife_name_jp = resultSet.getString("skill_dailylife_name_jp"),
					skill_dailylife_description = resultSet.getString("skill_dailylife_description"),
					skill_dailylife_description_jp = resultSet.getString("skill_dailylife_description_jp"),
					is_gacha = resultSet.getBoolean("is_gacha"),
					is_unavailable_gacha = resultSet.getBoolean("is_unavailable_gacha"),
					is_event = resultSet.getBoolean("is_event"),
					is_birthday = resultSet.getBoolean("is_birthday"),
					is_promo = resultSet.getBoolean("is_promo"),
					src_base_lq = resultSet.getString("src_base_lq"),
					src_idl_lq = resultSet.getString("src_idl_lq"),
					src_base_hq = resultSet.getString("src_base_hq"),
					src_idl_hq = resultSet.getString("src_idl_hq"),
				)
			}
		} catch (ex: EmptyResultDataAccessException) {
			null
		}

	fun findCardsPaginated(page: Int, resultsPerPage: Int): PaginatedCards =
		try {
			PaginatedCards(
				cardSummary = jdbcTemplate.queryForList(
					"SELECT * FROM $TABLE_NAME LIMIT :resultsPerPage OFFSET :page",
					MapSqlParameterSource()
						.addValue("page", page)
						.addValue("resultsPerPage", resultsPerPage)
				).map {
					mapToCardSummary(it)
				},
				totalPages = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM $TABLE_NAME", MapSqlParameterSource(), Integer::class.java) as Int
			)

		} catch (ex: EmptyResultDataAccessException) {
			PaginatedCards(emptyList(), 0)
		}

	fun findCardsPaginatedFilteredBy(
		page: Int,
		resultsPerPage: Int,
		characters: List<String>? = null,
		bands: List<String>? = null,
		rarities: List<Int>? = null,
		attributes: List<String>? = null,
		skill_session_types: List<String>? = null,
		is_gacha: Boolean? = null,
		is_unavailable_gacha: Boolean? = null,
		is_event: Boolean? = null,
		is_birthday: Boolean? = null,
		is_promo: Boolean? = null,
	): PaginatedCards = try {
		val sqlParameterSource = MapSqlParameterSource()
			.addValue("characters", characters)
			.addValue("bands", bands)
			.addValue("rarities", rarities)
			.addValue("attributes", attributes)
			.addValue("skill_session_types", skill_session_types)
			.addValue("is_gacha", is_gacha)
			.addValue("is_unavailable_gacha", is_unavailable_gacha)
			.addValue("is_event", is_event)
			.addValue("is_birthday", is_birthday)
			.addValue("is_promo", is_promo)
			.addValue("page", page)
			.addValue("resultsPerPage", resultsPerPage)
		val whereClause = "WHERE ((:characters::text) is null OR character_name in (:characters::text)) " +
			"AND ((:bands::text) is null OR band in (:bands::text)) " +
			"AND ((:rarities::integer) is null OR rarity in (:rarities::integer)) " +
			"AND ((:attributes::text) is null OR attribute in (:attributes::text)) " +
			"AND ((:skill_session_types::text) is null OR skill_session_type in (:skill_session_types::text)) " +
			"AND (:is_gacha::boolean is null OR is_gacha = :is_gacha::boolean) " +
			"AND (:is_unavailable_gacha::boolean is null OR is_unavailable_gacha = :is_unavailable_gacha::boolean) " +
			"AND (:is_event::boolean is null OR is_event = :is_event::boolean) " +
			"AND (:is_birthday::boolean is null OR is_birthday = :is_birthday::boolean) " +
			"AND (:is_promo::boolean is null OR is_promo = :is_promo::boolean) "
		PaginatedCards(
			cardSummary = jdbcTemplate.queryForList(
				"SELECT * FROM $TABLE_NAME " +
					whereClause +
					"LIMIT :resultsPerPage OFFSET :page",
				sqlParameterSource
			).map {
				mapToCardSummary(it)
			},
			totalPages = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM $TABLE_NAME $whereClause", sqlParameterSource, Integer::class.java) as Int
		)

	} catch (ex: EmptyResultDataAccessException) {
		PaginatedCards(emptyList(), 0)
	}

	private fun mapToCardSummary(it: MutableMap<String, Any>) = CardSummary(
		card_id = it["card_id"] as String,
		character_name = it["character_name"] as String,
		band = it["band"] as String,
		card_name = it["card_name"] as String,
		card_name_jp = it["card_name_jp"] as String,
		rarity = it["rarity"] as Int,
		attribute = it["attribute"] as String,
		src_base_lq = it["src_base_lq"] as String,
		src_idl_lq = it["src_idl_lq"] as String,
	)

}

package it.zampa.bangdudb.delivery.repository

import it.zampa.bangdudb.delivery.datamodel.out.CardSummary
import it.zampa.bangdudb.delivery.datamodel.out.Paginated
import it.zampa.bangdudb.domain.Card
import it.zampa.bangdudb.domain.repository.CardRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.sql.ResultSet

class DbCardRepository(val jdbcTemplate: NamedParameterJdbcTemplate) : CardRepository {

	val TABLE_NAME: String = "cards"

	override fun findById(cardId: String) =
		try {
			jdbcTemplate.queryForObject(
				"SELECT * FROM $TABLE_NAME WHERE id = :cardId",
				MapSqlParameterSource()
					.addValue("cardId", cardId)
			) { resultSet, _ ->
				mapToCard(resultSet)
			}
		} catch (ex: EmptyResultDataAccessException) {
			null
		}

	override fun findCardsFromEvent(eventId: Int): List<CardSummary> =
		try {
			jdbcTemplate.queryForList(
				"SELECT * FROM $TABLE_NAME WHERE event_id = :eventId",
				MapSqlParameterSource()
					.addValue("eventId", eventId)
			).map {
				mapToCardSummary(it)
			}
		} catch (ex: EmptyResultDataAccessException) {
			emptyList()
		}

	override fun findCardsPaginated(page: Int, resultsPerPage: Int): Paginated<CardSummary> =
		try {
			Paginated(
				summary = jdbcTemplate.queryForList(
					"SELECT * FROM $TABLE_NAME LIMIT :resultsPerPage OFFSET :offset",
					MapSqlParameterSource()
						.addValue("offset", page * resultsPerPage)
						.addValue("resultsPerPage", resultsPerPage)
				).map {
					mapToCardSummary(it)
				},
				totalPages = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM $TABLE_NAME", MapSqlParameterSource(), Integer::class.java) as Int
			)

		} catch (ex: EmptyResultDataAccessException) {
			Paginated(emptyList(), 0)
		}

	override fun findCardsPaginatedFilteredBy(
		page: Int,
		resultsPerPage: Int,
		characters: List<String>?,
		bands: List<String>?,
		rarities: List<Int>?,
		attributes: List<String>?,
		skill_session_types: List<String>?,
		is_gacha: Boolean?,
		is_unavailable_gacha: Boolean?,
		is_event: Boolean?,
		is_birthday: Boolean?,
		is_promo: Boolean?,
	): Paginated<CardSummary> = try {
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
			.addValue("offset", page * resultsPerPage)
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
		Paginated(
			summary = jdbcTemplate.queryForList(
				"SELECT * FROM $TABLE_NAME " +
					whereClause +
					"LIMIT :resultsPerPage OFFSET :offset",
				sqlParameterSource
			).map {
				mapToCardSummary(it)
			},
			totalPages = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM $TABLE_NAME $whereClause", sqlParameterSource, Integer::class.java) as Int
		)

	} catch (ex: EmptyResultDataAccessException) {
		Paginated(emptyList(), 0)
	}

	override fun save(card: Card) {
		jdbcTemplate.update("INSERT INTO $TABLE_NAME" +
			"( " +
			"id, " +
			"banner_id, " +
			"event_id, " +
			"character_name, " +
			"band, " +
			"card_name, " +
			"card_name_jp, " +
			"rarity, " +
			"attribute, " +
			"release_date, " +
			"power, " +
			"pf, " +
			"tec, " +
			"vi, " +
			"skill_session_name, " +
			"skill_session_name_jp, " +
			"skill_session_description, " +
			"skill_session_description_jp, " +
			"skill_session_type, " +
			"skill_dailylife_name, " +
			"skill_dailylife_name_jp, " +
			"skill_dailylife_description, " +
			"skill_dailylife_description_jp, " +
			"is_gacha, " +
			"is_unavailable_gacha, " +
			"is_event, " +
			"is_birthday, " +
			"is_promo, " +
			"src_base_lq, " +
			"src_idl_lq, " +
			"src_base_hq, " +
			"src_idl_hq, " +
			"src_avatar " +
			")" +
			" VALUES( " +
			":id, " +
			":banner_id, " +
			":event_id, " +
			":character_name, " +
			":band, " +
			":card_name, " +
			":card_name_jp, " +
			":rarity, " +
			":attribute, " +
			":release_date, " +
			":power, " +
			":pf, " +
			":tec, " +
			":vi, " +
			":skill_session_name, " +
			":skill_session_name_jp, " +
			":skill_session_description, " +
			":skill_session_description_jp, " +
			":skill_session_type, " +
			":skill_dailylife_name, " +
			":skill_dailylife_name_jp, " +
			":skill_dailylife_description, " +
			":skill_dailylife_description_jp, " +
			":is_gacha, " +
			":is_unavailable_gacha, " +
			":is_event, " +
			":is_birthday, " +
			":is_promo, " +
			":src_base_lq, " +
			":src_idl_lq, " +
			":src_base_hq, " +
			":src_idl_hq, " +
			":src_avatar " +
			")",
			MapSqlParameterSource()
				.addValue("id", card.id)
				.addValue("banner_id", card.banner_id)
				.addValue("event_id", card.event_id)
				.addValue("character_name", card.character_name)
				.addValue("band", card.band)
				.addValue("card_name", card.card_name)
				.addValue("card_name_jp", card.card_name_jp)
				.addValue("rarity", card.rarity)
				.addValue("attribute", card.attribute)
				.addValue("release_date", card.release_date)
				.addValue("power", card.power)
				.addValue("pf", card.pf)
				.addValue("tec", card.tec)
				.addValue("vi", card.vi)
				.addValue("skill_session_name", card.skill_session_name)
				.addValue("skill_session_name_jp", card.skill_session_name_jp)
				.addValue("skill_session_description", card.skill_session_description)
				.addValue("skill_session_description_jp", card.skill_session_description_jp)
				.addValue("skill_session_type", card.skill_session_type)
				.addValue("skill_dailylife_name", card.skill_dailylife_name)
				.addValue("skill_dailylife_name_jp", card.skill_dailylife_name_jp)
				.addValue("skill_dailylife_description", card.skill_dailylife_description)
				.addValue("skill_dailylife_description_jp", card.skill_dailylife_description_jp)
				.addValue("is_gacha", card.is_gacha)
				.addValue("is_unavailable_gacha", card.is_unavailable_gacha)
				.addValue("is_event", card.is_event)
				.addValue("is_birthday", card.is_birthday)
				.addValue("is_promo", card.is_promo)
				.addValue("src_base_lq", card.src_base_lq)
				.addValue("src_idl_lq", card.src_idl_lq)
				.addValue("src_base_hq", card.src_base_hq)
				.addValue("src_idl_hq", card.src_idl_hq)
				.addValue("src_avatar", card.src_avatar)
		)
	}

	private fun mapToCardSummary(it: MutableMap<String, Any>) = CardSummary(
		card_id = it["id"] as String,
		character_name = it["character_name"] as String,
		band = it["band"] as String,
		card_name = it["card_name"] as String,
		card_name_jp = it["card_name_jp"] as String,
		rarity = it["rarity"] as Int,
		attribute = it["attribute"] as String,
		src_base_lq = it["src_base_lq"] as String,
		src_idl_lq = it["src_idl_lq"] as String,
	)

	private fun mapToCard(resultSet: ResultSet) = Card(
		id = resultSet.getString("id"),
		banner_id = resultSet.getInt("banner_id"),
		event_id = resultSet.getInt("event_id"),
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
		src_avatar = resultSet.getString("src_avatar")
	)
}

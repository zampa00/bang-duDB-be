package it.zampa.bangdudb.repository

import it.zampa.bangdudb.delivery.datamodel.CardSummary
import it.zampa.bangdudb.domain.Card
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CardRepository : PagingAndSortingRepository<Card, String> {

	@Query("select * from cards", nativeQuery = true)
	fun findCards(): List<Card>

	@Query("select * from cards where card_id = :id", nativeQuery = true)
	fun findCard(
		@Param("id") cardId: String
	): Card?

	@Query("select * from cards where " +
		"((:characters::varchar) is null OR character_name in (:characters::varchar)) " +
		"AND ((:bands::varchar) is null OR band in (:bands::varchar)) " +
		"AND ((:rarities::integer) is null OR rarity in (:rarities::integer)) " +
		"AND ((:attributes::varchar) is null OR attribute in (:attributes::varchar)) " +
		"AND ((:skill_session_types::varchar) is null OR skill_session_type in (:skill_session_types::varchar)) " +
		"AND (:is_gacha is null OR is_gacha = :is_gacha) " +
		"AND (:is_unavailable_gacha is null OR is_unavailable_gacha = :is_unavailable_gacha) " +
		"AND (:is_event is null OR is_event = :is_event) " +
		"AND (:is_birthday is null OR is_birthday = :is_birthday) " +
		"AND (:is_promo is null OR is_promo = :is_promo) " +
		"", nativeQuery = true
	)
	fun findCards(
		@Param("characters") characters: List<String>?,
		@Param("bands") bands: List<String>?,
		@Param("rarities") rarities: List<Int>?,
		@Param("attributes") attributes: List<String>?,
		@Param("skill_session_types") skill_session_types: List<String>?,
		@Param("is_gacha") is_gacha: Boolean?,
		@Param("is_unavailable_gacha") is_unavailable_gacha: Boolean?,
		@Param("is_event") is_event: Boolean?,
		@Param("is_birthday") is_birthday: Boolean?,
		@Param("is_promo") is_promo: Boolean?,
	): List<Card>

	@Query("SELECT * FROM CARDS WHERE banner_id = :banner_id", nativeQuery = true)
	fun findCardsInBanner(
		@Param("banner_id") banner_id: Int
	): List<Card>

	@Query("select card_id, " +
		"character_name, " +
		"band, card_name, " +
		"card_name_jp, " +
		"rarity, " +
		"attribute, " +
		"src_base_lq, " +
		"src_idl_lq " +
		"from cards " +
		"where :event_id = id", nativeQuery = true)
	fun findEventCards(@Param("event_id") eventId: Int): List<CardSummary>


}


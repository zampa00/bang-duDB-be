package it.zampa.bangdudb.repository

import it.zampa.bangdudb.domain.Card
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface CardRepository : CrudRepository<Card, String> {

	@Query("select * from cards")
	fun findCards(): List<Card>

	@Query("select * from cards where id = :id")
	fun findCard(
		@Param("id") cardId: String
	): Card?

	@Query("select * from cards where " +
		"(:characters is null OR character_name in (:characters)) " +
		"AND (:bands is null OR band in (:bands)) " +
		"AND (:rarities is null OR rarity in (:rarities)) " +
		"AND (:attributes is null OR attribute in (:attributes)) " +
		"AND (:skill_session_types is null OR skill_session_type in (:skill_session_types)) " +
		"AND (:is_gacha is null OR is_gacha = :is_gacha) " +
		"AND (:is_unavailable_gacha is null OR is_unavailable_gacha = :is_unavailable_gacha) " +
		"AND (:is_birthday is null OR is_birthday = :is_birthday) " +
		"AND (:is_promo is null OR is_promo = :is_promo) " +
		""
	)
	fun findCards(
		@Param("characters") characters: List<String>?,
		@Param("bands") bands: List<String>?,
		@Param("rarities") rarities: List<String>?,
		@Param("attributes") attributes: List<String>?,
		@Param("skill_session_types") skill_session_types: List<String>?,
		@Param("is_gacha") is_gacha: Boolean?,
		@Param("is_unavailable_gacha") is_unavailable_gacha: Boolean?,
		@Param("is_birthday") is_birthday: Boolean?,
		@Param("is_promo") is_promo: Boolean?,
	): List<Card>


}


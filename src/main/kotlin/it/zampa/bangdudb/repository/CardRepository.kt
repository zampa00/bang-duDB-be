package it.zampa.bangdudb.repository

import it.zampa.bangdudb.delivery.datamodel.CardSummary
import it.zampa.bangdudb.delivery.datamodel.Paginated
import it.zampa.bangdudb.domain.Card

interface CardRepository {
	fun save(card: Card)
	fun findById(cardId: String): Card?
	fun findCardsPaginated(page: Int, resultsPerPage: Int): Paginated<CardSummary>
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
	): Paginated<CardSummary>
}
package it.zampa.bangdudb.delivery

import it.zampa.bangdudb.domain.Card
import it.zampa.bangdudb.repository.CardRepository
import org.springframework.stereotype.Service

@Service
class CardService(val db: CardRepository) {

	fun findCards(): List<Card> = db.findCards()

	fun findCard(cardId: String): Card? = db.findCard(cardId)

	fun findCards(
		characters: List<String>?,
		bands: List<String>?,
		rarities: List<String>?,
		attributes: List<String>?,
		skill_session_types: List<String>?,
		is_gacha: Boolean?,
		is_unavailable_gacha: Boolean?,
		is_event: Boolean?,
		is_birthday: Boolean?,
		is_promo: Boolean?,
	): List<Card> {
		System.out.println(rarities)
		return db.findCards(
			characters, bands, rarities, attributes, skill_session_types, is_gacha, is_unavailable_gacha, is_event, is_birthday, is_promo
		)
	}

	fun addCard(card: Card) {
		db.save(card)
	}

}
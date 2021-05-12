package it.zampa.bangdudb.delivery

import it.zampa.bangdudb.domain.Card
import it.zampa.bangdudb.repository.CardRepository
import org.springframework.stereotype.Service

@Service
class CardService(val db: CardRepository) {

	fun findCards(): List<Card> = db.findCards()

	fun findCard(cardId: String): Card? = db.findCard(cardId)

	fun findCards(characters: List<String>): List<Card> = db.findCards(characters)

	fun post(card: Card) {
		db.save(card)
	}

}
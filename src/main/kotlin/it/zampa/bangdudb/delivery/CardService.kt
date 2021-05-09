package it.zampa.bangdudb.delivery

import it.zampa.bangdudb.domain.Card
import it.zampa.bangdudb.repository.CardRepository
import org.springframework.stereotype.Service

@Service
class CardService(val db: CardRepository) {

	fun findMessages(): List<Card> = db.findCards()

	fun post(card: Card) {
		db.save(card)
	}

}
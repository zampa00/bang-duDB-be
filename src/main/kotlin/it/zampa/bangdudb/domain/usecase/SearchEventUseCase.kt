package it.zampa.bangdudb.domain.usecase

import it.zampa.bangdudb.delivery.datamodel.out.CardSummary
import it.zampa.bangdudb.delivery.datamodel.out.EventWithCards
import it.zampa.bangdudb.delivery.datamodel.out.ListItem
import it.zampa.bangdudb.domain.Event
import it.zampa.bangdudb.domain.repository.CardRepository
import it.zampa.bangdudb.domain.repository.EventRepository

class SearchEventUseCase(
	private val cardRepository: CardRepository,
	private val eventRepository: EventRepository
) {

	fun search(eventId: Int): EventWithCards {
		val event: Event? = eventRepository.findById(eventId)
		val cards: List<CardSummary>? = event?.id?.let { cardRepository.findCardsFromEvent(it) }
		return EventWithCards(
			event = event,
			cards = cards
		)
	}

	fun getAllEventsIds(): List<ListItem> =
		eventRepository.findEventsForListing()

}
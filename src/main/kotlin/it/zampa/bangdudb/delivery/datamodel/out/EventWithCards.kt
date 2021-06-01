package it.zampa.bangdudb.delivery.datamodel.out

import it.zampa.bangdudb.domain.Event

data class EventWithCards(
	val event: Event?,
	val cards: List<CardSummary>?
)

package it.zampa.bangdudb.delivery.datamodel

import it.zampa.bangdudb.domain.Event

data class EventWithCards(
	val event: Event?,
	val cards: List<CardSummary>?
)

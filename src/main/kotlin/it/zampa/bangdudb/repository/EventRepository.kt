package it.zampa.bangdudb.repository

import it.zampa.bangdudb.delivery.datamodel.EventSummary
import it.zampa.bangdudb.delivery.datamodel.Paginated
import it.zampa.bangdudb.domain.Event

interface EventRepository {
	fun findById(eventId: Int): Event?
	fun findEventsPaginated(page: Int, resultsPerPage: Int): Paginated<EventSummary>
	fun save(event: Event)
}
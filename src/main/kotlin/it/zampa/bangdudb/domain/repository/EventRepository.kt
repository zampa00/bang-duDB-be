package it.zampa.bangdudb.domain.repository

import it.zampa.bangdudb.delivery.datamodel.out.EventSummary
import it.zampa.bangdudb.delivery.datamodel.out.Paginated
import it.zampa.bangdudb.domain.Event

interface EventRepository {
	fun findById(eventId: Int): Event?
	fun findEventsPaginated(page: Int, resultsPerPage: Int): Paginated<EventSummary>
	fun save(event: Event)
}
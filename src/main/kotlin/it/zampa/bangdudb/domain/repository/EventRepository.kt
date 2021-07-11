package it.zampa.bangdudb.domain.repository

import it.zampa.bangdudb.delivery.datamodel.out.EventSummary
import it.zampa.bangdudb.delivery.datamodel.out.ListItem
import it.zampa.bangdudb.delivery.datamodel.out.Paginated
import it.zampa.bangdudb.domain.Event

interface EventRepository {
	fun findById(eventId: Int): Event?
	fun findEventsForListing(): List<ListItem>
	fun findEventsPaginated(page: Int, resultsPerPage: Int): Paginated<EventSummary>
	fun save(event: Event)
	fun editEvent(event: Event)
}
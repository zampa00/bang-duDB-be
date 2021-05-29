package it.zampa.bangdudb.delivery

import it.zampa.bangdudb.delivery.datamodel.EventSummary
import it.zampa.bangdudb.domain.Event
import it.zampa.bangdudb.repository.EventRepository
import org.springframework.stereotype.Service

@Service
class EventService(val db: EventRepository) {

	fun findEvents(): List<EventSummary> = db.findAllEvents()

	fun findEvent(eventId: Int): Event? = db.findEvent(eventId)

	fun findEventsSummary(): List<EventSummary> = db.findAllEventsSummary()

}
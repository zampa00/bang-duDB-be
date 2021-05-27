package it.zampa.bangdudb.delivery

import it.zampa.bangdudb.domain.Event
import it.zampa.bangdudb.repository.EventRepository
import org.springframework.stereotype.Service

@Service
class EventService(val db: EventRepository) {

	fun findEvents(): List<Event> = db.findAllEvents()

	fun findEvent(eventId: Int): Event? = db.findEvent(eventId)

}
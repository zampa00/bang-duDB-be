package it.zampa.bangdudb.delivery

import it.zampa.bangdudb.delivery.datamodel.EventSummary
import it.zampa.bangdudb.domain.Event
import it.zampa.bangdudb.repository.EventRepository
import org.springframework.stereotype.Service

@Service
class EventService(val db: EventRepository) {

	fun findEvent(eventId: Int): Event? = db.findEvent(eventId)

	fun findEventsSummary(): List<EventSummary> {
		return db.findAllEvents().map {
			EventSummary(
				id = it.id!!,
				name = it.name,
				name_jp = it.name_jp,
				image_lq = it.image_lq
			)
		}
	}

}
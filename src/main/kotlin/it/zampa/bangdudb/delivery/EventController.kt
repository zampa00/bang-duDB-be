package it.zampa.bangdudb.delivery

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import it.zampa.bangdudb.domain.Event
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
class EventController(val service: EventService) {

	var logger: Logger = LoggerFactory.getLogger(this::class.java)

	private val mapper = ObjectMapper()
		.registerModule(KotlinModule())
		.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

	@GetMapping("/events")
	fun getEvents(): List<Event> {
		return service.findEvents()
	}

	@GetMapping("/event/{eventId}")
	@ResponseBody
	fun getEventFromId(@PathVariable eventId: Int): Event? {
		return service.findEvent(eventId)
	}

}
package it.zampa.bangdudb.delivery.controller

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import it.zampa.bangdudb.delivery.datamodel.`in`.InputEvent
import it.zampa.bangdudb.delivery.datamodel.out.EventSummary
import it.zampa.bangdudb.delivery.datamodel.out.Paginated
import it.zampa.bangdudb.domain.Event
import it.zampa.bangdudb.domain.repository.EventRepository
import it.zampa.bangdudb.domain.usecase.AddEventUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@CrossOrigin
class EventController(
	val eventRepository: EventRepository,
	val addEventUseCase: AddEventUseCase
) {

	var logger: Logger = LoggerFactory.getLogger(this::class.java)

	private val mapper = ObjectMapper()
		.registerModule(KotlinModule())
		.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

	@GetMapping("/events")
	fun getEvents(@RequestParam page: Int, @RequestParam size: Int): Paginated<EventSummary> {
		return eventRepository.findEventsPaginated(page, size)
	}

	@GetMapping("/event/{eventId}")
	@ResponseBody
	fun getEventFromId(@PathVariable eventId: Int): Event? {
		return eventRepository.findById(eventId)
	}

	@PostMapping("/addEvent")
	fun addEvent(
		@RequestParam eventData: String,
		@RequestParam banner: MultipartFile,
		@RequestParam stamp: MultipartFile,
		@RequestParam titlePoints: MultipartFile,
		@RequestParam titleRank: MultipartFile,
		@RequestParam instrument: MultipartFile,
		@RequestParam accessoryPoints: MultipartFile,
		@RequestParam accessoryRank: MultipartFile
	): ResponseEntity<String> {
		logger.info("received $eventData")

		val event = mapper.readValue(eventData, InputEvent::class.java)

		logger.info("mapped to $event")

		addEventUseCase.execute(event,
			banner,
			stamp,
			titlePoints,
			titleRank,
			instrument,
			accessoryPoints,
			accessoryRank)
		return ResponseEntity<String>(HttpStatus.OK)
	}

//	@GetMapping("/eventDetails/{eventId}")
//	@ResponseBody
//	fun getEventWithCards(@PathVariable eventId: Int): EventWithCards {
//		val event = eventService.findEvent(eventId)
//		val cards = event?.id?.let { cardService.findAllCardsOfEvent(it) }
//		return EventWithCards(event, cards)
//	}

}
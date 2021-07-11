package it.zampa.bangdudb.domain.usecase

import it.zampa.bangdudb.delivery.datamodel.`in`.InputEvent
import it.zampa.bangdudb.domain.Event
import it.zampa.bangdudb.domain.repository.EventRepository
import java.time.LocalDate

class EditEventUseCase(
	private val eventRepository: EventRepository
) {

	fun execute(event: InputEvent) {
		val oldEvent = eventRepository.findById(event.id!!)!!
		val newEvent = oldEvent.ovverrideWith(event)
		eventRepository.editEvent(newEvent)
	}

}

private fun Event.ovverrideWith(inputEvent: InputEvent): Event = Event(
	id = this.id,
	name = inputEvent.name,
	name_jp = inputEvent.nameJp,
	description = inputEvent.description,
	description_jp = inputEvent.descriptionJp,
	start_date = LocalDate.parse(inputEvent.startDate),
	end_date = LocalDate.parse(inputEvent.endDate),
	image_hq = this.image_hq,
	stamp = this.stamp,
	title_point = this.title_point,
	title_rank = this.title_rank,
	instrument = this.instrument,
	accessory_point = this.accessory_point,
	accessory_rank = this.accessory_rank
)

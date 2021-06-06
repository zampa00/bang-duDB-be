package it.zampa.bangdudb.domain.usecase

import it.zampa.bangdudb.delivery.datamodel.`in`.InputEvent
import it.zampa.bangdudb.domain.Event
import it.zampa.bangdudb.domain.repository.EventRepository
import it.zampa.bangdudb.domain.service.ImageUploader
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

class AddEventUseCase(
	val imageUploader: ImageUploader,
	val eventRepository: EventRepository
) {

	var logger: Logger = LoggerFactory.getLogger(this::class.java)

	fun execute(
		eventData: InputEvent,
		banner: MultipartFile,
		stamp: MultipartFile,
		titlePoints: MultipartFile,
		titleRank: MultipartFile,
		instrument: MultipartFile,
		accessoryPoints: MultipartFile,
		accessoryRank: MultipartFile
	) {
		logger.info("AddEventUseCase start")

		val bannerHqUrl = imageUploader.upload(banner.inputStream, banner.resource.filename!!)
		val stampUrl = imageUploader.upload(stamp.inputStream, stamp.resource.filename!!)
		val titlePointsUrl = imageUploader.upload(titlePoints.inputStream, stamp.resource.filename!!)
		val titleRankUrl = imageUploader.upload(titleRank.inputStream, titleRank.resource.filename!!)
		val instrumentUrl = imageUploader.upload(instrument.inputStream, instrument.resource.filename!!)
		val accessoryPointsUrl = imageUploader.upload(accessoryPoints.inputStream, accessoryPoints.resource.filename!!)
		val accessoryRankUrl = imageUploader.upload(accessoryRank.inputStream, accessoryRank.resource.filename!!)

		logger.info("all event's image uploaded")

		val eventToSave: Event = eventData.mapToDomain(
			bannerHqUrl,
			stampUrl,
			titlePointsUrl,
			titleRankUrl,
			instrumentUrl,
			accessoryPointsUrl,
			accessoryRankUrl
		)

		logger.info("mapper event to domain: $eventToSave")

		eventRepository.save(eventToSave)

		logger.info("event saved")
	}

}

private fun InputEvent.mapToDomain(
	imgHqUrl: String,
	stampUrl: String,
	titlePointsUrl: String,
	titleRankUrl: String,
	instrumentUrl: String,
	accessoryPointsUrl: String,
	accessoryRankUrl: String
): Event {
	return Event(
		name = this.name,
		name_jp = this.nameJp,
		description = this.description,
		description_jp = this.descriptionJp,
		start_date = LocalDate.parse(this.startDate)!!,
		end_date = LocalDate.parse(this.endDate)!!,
		image_hq = imgHqUrl,
		stamp = stampUrl,
		title_point = titlePointsUrl,
		title_rank = titleRankUrl,
		instrument = instrumentUrl,
		accessory_point = accessoryPointsUrl,
		accessory_rank = accessoryRankUrl,
	)
}

private fun String.nameWithoutExtension(): String = this.substringBeforeLast(".")
package it.zampa.bangdudb.domain.usecase

import it.zampa.bangdudb.delivery.datamodel.`in`.InputCard
import it.zampa.bangdudb.domain.Card
import it.zampa.bangdudb.domain.repository.CardRepository
import it.zampa.bangdudb.domain.service.ImageCompressionService
import it.zampa.bangdudb.domain.service.ImageUploader
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

class AddCardUseCase(val imageUploader: ImageUploader, val cardRepository: CardRepository, val imageCompressionService: ImageCompressionService) {

	var logger: Logger = LoggerFactory.getLogger(this::class.java)

	fun execute(card: InputCard, imgBase: MultipartFile, imgIdl: MultipartFile, imgAvatar: MultipartFile) {

		logger.info("AddCardUseCase start")

		val imgBaseHqUrl = imageUploader.upload(imgBase.inputStream, imgBase.resource.filename!!)
		val imgIdlHqUrl = imageUploader.upload(imgIdl.inputStream, imgIdl.resource.filename!!)
		val imgAvatarUrl = imageUploader.upload(imgAvatar.inputStream, imgAvatar.resource.filename!!)

		val imgBaseLq = imageCompressionService.compress(imgBase.inputStream, imgBase.resource.filename!!.nameWithoutExtension())
		val imgIdlLq = imageCompressionService.compress(imgIdl.inputStream, imgIdl.resource.filename!!.nameWithoutExtension())
		val imgBaseLqUrl = imageUploader.upload(imgBaseLq, imgBaseLq.name)
		val imgIdlLqUrl = imageUploader.upload(imgIdlLq, imgIdlLq.name)

		logger.info("all cards uploaded")

		val cardToSave: Card = card.mapToDomain(imgBaseHqUrl, imgIdlHqUrl, imgBaseLqUrl, imgIdlLqUrl, imgAvatarUrl)

		logger.info("card mapped to domain card ${cardToSave}")

		cardRepository.save(cardToSave)

		logger.info("card saved")

		imgBaseLq.delete()
		imgIdlLq.delete()
	}
}

private fun String.nameWithoutExtension(): String = this.substringBeforeLast(".")

private fun InputCard.mapToDomain(imgBaseHqUrl: String, imgIdlHqUrl: String, imgBaseLqUrl: String, imgIdlLqUrl: String, imgAvatarUrl: String): Card = Card(
	id = this.id,
	banner_id = this.banner_id,
	event_id = this.event_id,
	character_name = this.character,
	band = this.band,
	card_name = this.cardName,
	card_name_jp = this.cardNameJp,
	rarity = this.rarity,
	attribute = this.attribute,
	release_date = LocalDate.parse(this.releaseDate.substring(0, 10))!!,
	power = this.power,
	pf = this.pf,
	tec = this.tec,
	vi = this.vi,
	skill_session_name = this.skillSessionName,
	skill_session_name_jp = this.skillSessionNameJp,
	skill_session_description = this.skillSessionDescription,
	skill_session_description_jp = this.skillSessionDescriptionJp,
	skill_session_type = this.skilltype,
	skill_dailylife_name = this.skillDailylifeName,
	skill_dailylife_name_jp = this.skillDailylifeNameJp,
	skill_dailylife_description = this.skillDailylifeDescription,
	skill_dailylife_description_jp = this.skillDailylifeDescriptionJp,
	is_gacha = this.isGacha,
	is_unavailable_gacha = this.isUnavailableGacha,
	is_event = this.isEvent,
	is_birthday = this.isBirthday,
	is_promo = this.isPromo,
	src_base_lq = imgBaseLqUrl,
	src_idl_lq = imgIdlLqUrl,
	src_base_hq = imgBaseHqUrl,
	src_idl_hq = imgIdlHqUrl,
	src_avatar = imgAvatarUrl
)

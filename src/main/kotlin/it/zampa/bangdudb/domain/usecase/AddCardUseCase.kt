package it.zampa.bangdudb.domain.usecase

import it.zampa.bangdudb.delivery.data.InputCard
import it.zampa.bangdudb.domain.Card
import it.zampa.bangdudb.domain.ImageUploader
import it.zampa.bangdudb.repository.CardRepository
import it.zampa.bangdudb.utils.ImageCompressionService
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

class AddCardUseCase(val imageUploader: ImageUploader, val cardRepository: CardRepository, val imageCompressionService: ImageCompressionService) {

	fun execute(card: InputCard, imgBase: MultipartFile, imgIdl: MultipartFile) {

		val imgBaseHqUrl = imageUploader.uploadCard(imgBase.inputStream, imgBase.resource.filename!!)
		val imgIdlHqUrl = imageUploader.uploadCard(imgIdl.inputStream, imgIdl.resource.filename!!)

		val imgBaseLq = imageCompressionService.compress(imgBase.inputStream, imgBase.resource.filename!!.nameWithoutExtension())
		val imgIdlLq = imageCompressionService.compress(imgIdl.inputStream, imgIdl.resource.filename!!.nameWithoutExtension())
		val imgBaseLqUrl = imageUploader.uploadCard(imgBaseLq, imgBaseLq.name)
		val imgIdlLqUrl = imageUploader.uploadCard(imgIdlLq, imgIdlLq.name)

		val cardToSave: Card = card.mapToDomain(imgBaseHqUrl, imgIdlHqUrl, imgBaseLqUrl, imgIdlLqUrl)

		cardRepository.save(cardToSave)

		imgBaseLq.delete()
		imgIdlLq.delete()
	}
}

private fun String.nameWithoutExtension(): String = this.substringBeforeLast(".")

private fun InputCard.mapToDomain(imgBaseHqUrl: String, imgIdlHqUrl: String, imgBaseLqUrl: String, imgIdlLqUrl: String): Card = Card(
	card_id = this.id,
	character_name = this.characters,
	band = this.band,
	card_name = this.cardName,
	rarity = this.rarities,
	attribute = this.attributes,
	release_date = LocalDate.parse(this.releaseDate.substring(0, 10))!!,
	power = this.power,
	pf = this.pf,
	tec = this.tec,
	vi = this.vi,
	skill_session_name = this.skillSessionName,
	skill_session_description = this.skillSessionDescription,
	skill_session_type = this.skilltype,
	skill_dailylife_name = this.skillDailylifeName,
	skill_dailylife_description = this.skillDailylifeDescription,
	is_gacha = this.isGacha,
	is_unavailable_gacha = this.isUnavailableGacha,
	is_event = this.isEvent,
	is_birthday = this.isBirthday,
	is_promo = this.isPromo,
	src_base_lq = imgBaseLqUrl,
	src_idl_lq = imgIdlLqUrl,
	src_base_hq = imgBaseHqUrl,
	src_idl_hq = imgIdlHqUrl
)

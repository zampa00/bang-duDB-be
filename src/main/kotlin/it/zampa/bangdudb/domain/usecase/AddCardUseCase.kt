package it.zampa.bangdudb.domain.usecase

import it.zampa.bangdudb.delivery.data.InputCard
import it.zampa.bangdudb.domain.Card
import it.zampa.bangdudb.domain.ImageUploader
import it.zampa.bangdudb.repository.CardRepository
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

class AddCardUseCase(val imageUploader: ImageUploader, val cardRepository: CardRepository) {
	fun execute(card: InputCard, imgBase: MultipartFile, imgIdl: MultipartFile) {

		val imgBaseHqUrl = imageUploader.uploadCard(imgBase, imgBase.resource.filename!!)
		val imgIdlHqUrl = imageUploader.uploadCard(imgIdl, imgIdl.resource.filename!!)

		val cardToSave: Card = card.mapToDomain(imgBaseHqUrl, imgIdlHqUrl)

		cardRepository.save(cardToSave)
	}
}

val baseCardUrl = "https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/"

private fun InputCard.mapToDomain(imgBaseHqUrl: String, imgIdlHqUrl: String): Card = Card(
	id = this.id,
	character_name = this.characters,
	band = this.band,
	card_name = this.cardName,
	rarity = this.rarities,
	attribute = this.attributes,
	release_date = LocalDate.parse(this.releaseDate)!!,
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
	src_base_lq = "${baseCardUrl}001_0001_1_lq.jpg",
	src_idl_lq = "${baseCardUrl}001_0001_2_lq.jpg",
	src_base_hq = imgBaseHqUrl,
	src_idl_hq = imgIdlHqUrl
)

package it.zampa.bangdudb.domain.usecase

import it.zampa.bangdudb.delivery.datamodel.`in`.InputCard
import it.zampa.bangdudb.domain.Card
import it.zampa.bangdudb.domain.repository.CardRepository
import java.time.LocalDate

class EditCardUseCase(
	val cardRepository: CardRepository
) {

	fun execute(newInputCard: InputCard) {
		val oldCard = cardRepository.findById(newInputCard.id)!!
		val newCard = oldCard.overrideWith(newInputCard)
		cardRepository.editCard(newCard)
	}

}

private fun Card.overrideWith(newInputCard: InputCard): Card = Card(
	id = this.id,
	banner_id = newInputCard.banner_id,
	event_id = newInputCard.event_id,
	character_name = newInputCard.character,
	band = newInputCard.band,
	card_name = newInputCard.cardName,
	card_name_jp = newInputCard.cardNameJp,
	rarity = newInputCard.rarity,
	attribute = newInputCard.attribute,
	release_date = LocalDate.parse(newInputCard.releaseDate.substring(0, 10))!!,
	power = newInputCard.power,
	pf = newInputCard.pf,
	tec = newInputCard.tec,
	vi = newInputCard.vi,
	skill_session_name = newInputCard.skillSessionName,
	skill_session_name_jp = newInputCard.skillSessionNameJp,
	skill_session_description = newInputCard.skillSessionDescription,
	skill_session_description_jp = newInputCard.skillSessionDescriptionJp,
	skill_session_type = newInputCard.skilltype,
	skill_dailylife_name = newInputCard.skillDailylifeName,
	skill_dailylife_name_jp = newInputCard.skillDailylifeNameJp,
	skill_dailylife_description = newInputCard.skillDailylifeDescription,
	skill_dailylife_description_jp = newInputCard.skillDailylifeDescriptionJp,
	is_gacha = newInputCard.isGacha,
	is_unavailable_gacha = newInputCard.isUnavailableGacha,
	is_event = newInputCard.isEvent,
	is_birthday = newInputCard.isBirthday,
	is_promo = newInputCard.isPromo,
	src_base_lq = this.src_base_lq,
	src_idl_lq = this.src_idl_lq,
	src_base_hq = this.src_base_hq,
	src_idl_hq = this.src_idl_hq,
	src_avatar = this.src_avatar,
)

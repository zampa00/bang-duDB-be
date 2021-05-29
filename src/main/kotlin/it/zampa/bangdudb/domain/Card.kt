package it.zampa.bangdudb.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("cards")
data class Card(

	@Id
	val id: String? = null,

	val banner_id: Int?,
	val event_id: Int?,

	val card_id: String,
	val character_name: String,
	val band: String,
	val card_name: String,
	val card_name_jp: String,
	val rarity: Int,
	val attribute: String,
	val release_date: LocalDate,

	val power: Int,
	val pf: Int,
	val tec: Int,
	val vi: Int,

	val skill_session_name: String,
	val skill_session_name_jp: String,
	val skill_session_description: String,
	val skill_session_description_jp: String,
	val skill_session_type: String,
	val skill_dailylife_name: String,
	val skill_dailylife_name_jp: String,
	val skill_dailylife_description: String,
	val skill_dailylife_description_jp: String,

	val is_gacha: Boolean,
	val is_unavailable_gacha: Boolean,
	val is_event: Boolean,
	val is_birthday: Boolean,
	val is_promo: Boolean,

	val src_base_lq: String,
	val src_idl_lq: String,
	val src_base_hq: String,
	val src_idl_hq: String
)


package it.zampa.bangdudb.delivery.datamodel.out

data class CardSummary(
	val card_id: String,

	val character_name: String,
	val band: String,
	val card_name: String,
	val card_name_jp: String,
	val rarity: Int,
	val attribute: String,

	val src_base_lq: String,
	val src_idl_lq: String,
)

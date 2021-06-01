package it.zampa.bangdudb.delivery.datamodel.`in`

data class InputCard(
	val id: String,
	val banner_id: Int?,
	val event_id: Int?,

	val character: String,
	val band: String,
	val cardName: String,
	val cardNameJp: String,
	val rarity: Int,
	val attribute: String,
	val releaseDate: String,

	val power: Int,
	val pf: Int,
	val tec: Int,
	val vi: Int,

	val skillSessionName: String,
	val skillSessionNameJp: String,
	val skillSessionDescription: String,
	val skillSessionDescriptionJp: String,
	val skilltype: String,
	val skillDailylifeName: String,
	val skillDailylifeNameJp: String,
	val skillDailylifeDescription: String,
	val skillDailylifeDescriptionJp: String,

	val isGacha: Boolean,
	val isUnavailableGacha: Boolean,
	val isEvent: Boolean,
	val isBirthday: Boolean,
	val isPromo: Boolean,
)
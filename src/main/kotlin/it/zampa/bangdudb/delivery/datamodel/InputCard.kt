package it.zampa.bangdudb.delivery.datamodel

data class InputCard(
	val id: String,
	val banner_id: String,

	val characters: String,
	val band: String,
	val cardName: String,
	val rarities: String,
	val attributes: String,
	val releaseDate: String,

	val power: Int,
	val pf: Int,
	val tec: Int,
	val vi: Int,

	val skillSessionName: String,
	val skillSessionDescription: String,
	val skilltype: String,
	val skillDailylifeName: String,
	val skillDailylifeDescription: String,

	val isGacha: Boolean,
	val isUnavailableGacha: Boolean,
	val isEvent: Boolean,
	val isBirthday: Boolean,
	val isPromo: Boolean,
)
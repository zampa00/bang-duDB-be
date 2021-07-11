package it.zampa.bangdudb.delivery.datamodel.`in`

data class InputBanner(
	val id: Int? = null,
	val name: String,
	val nameJp: String,
	val description: String,
	val descriptionJp: String,

	val startDate: String,
	val endDate: String,
)
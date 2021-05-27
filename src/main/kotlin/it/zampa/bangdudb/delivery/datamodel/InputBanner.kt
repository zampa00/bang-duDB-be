package it.zampa.bangdudb.delivery.datamodel

data class InputBanner(
	val name: String,
	val nameJp: String,
	val description: String,
	val descriptionJp: String,

	val startDate: String,
	val endDate: String,
)
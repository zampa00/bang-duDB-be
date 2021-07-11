package it.zampa.bangdudb.domain

import java.time.LocalDate

data class Banner(
	var id: Int? = null,

	var name: String,
	var name_jp: String,
	var description: String,
	var description_jp: String,

	var start_date: LocalDate,
	var end_date: LocalDate,

	var image_hq: String?,
)
package it.zampa.bangdudb.delivery.datamodel.out

import java.time.LocalDate

data class SongSummary(
	val name: String,
	val name_jp: String,
	val band: String,
	val is_cover: Boolean,
	val release_date: LocalDate,
	val image: String?
)

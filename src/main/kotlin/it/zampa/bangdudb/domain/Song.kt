package it.zampa.bangdudb.domain

import java.time.LocalDate

data class Song(
	val id: Int? = null,
	val name: String,
	val name_jp: String,
	val band: String,
	val is_cover: Boolean,
	val release_date: LocalDate,
	val image: String?
)

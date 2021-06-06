package it.zampa.bangdudb.domain

import java.time.LocalDate

data class Song(
	val name: String,
	val name_jp: String,
	val band: String,
	val lyricist: String,
	val composer: String,
	val arranger: String,
	val difficulty: String,
	val other_info: String,
	val is_cover: Boolean,
	val release_date: LocalDate,
	val image: String?
)

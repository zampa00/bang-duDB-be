package it.zampa.bangdudb.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("events")
data class Event(
	@Id
	val id: String? = null,

	val name: String,
	val name_jp: String,
	val description: String,
	val description_jp: String,

	val start_date: LocalDate,
	val end_date: LocalDate,

	val image_hq: String?,
	val image_lq: String?,
	val stamp: String,
	val title_point: String,
	val title_rank: String,
	val instrument: String,
	val accessory_point: String,
	val accessory_rank: String
)
package it.zampa.bangdudb.domain

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "events")
class Event(
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	val id: Int? = null,

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
package it.zampa.bangdudb.domain

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "banners")
class Banner(
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	var id: String? = null,

	var name: String,
	var name_jp: String,
	var description: String,
	var description_jp: String,

	var start_date: LocalDate,
	var end_date: LocalDate,

	var image_hq: String?,
	var image_lq: String?
)
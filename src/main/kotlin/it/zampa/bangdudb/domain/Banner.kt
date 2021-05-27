package it.zampa.bangdudb.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("banners")
data class Banner(
	@Id
	val id: String? = null,

	val name: String,
	val name_jp: String,
	val description: String,
	val description_jp: String,

	val start_date: LocalDate,
	val end_date: LocalDate,

	val image_hq: String?,
	val image_lq: String?
)
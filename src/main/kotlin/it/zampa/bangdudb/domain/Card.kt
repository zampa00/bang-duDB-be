package it.zampa.bangdudb.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("CARDS")
data class Card(
	@Id val id: String?,
	val character_name: String,
	val card_name: String,
	val src_base: String,
	val src_idl: String,
)


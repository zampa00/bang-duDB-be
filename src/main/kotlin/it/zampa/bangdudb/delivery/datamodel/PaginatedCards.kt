package it.zampa.bangdudb.delivery.datamodel

data class PaginatedCards(
	val cardSummary: List<CardSummary>,
	val totalPages: Int
)

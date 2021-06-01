package it.zampa.bangdudb.delivery.datamodel

data class Paginated<T>(
	val summary: List<T>,
	val totalPages: Int
)

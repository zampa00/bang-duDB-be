package it.zampa.bangdudb.delivery.datamodel.out

data class Paginated<T>(
	val summary: List<T>,
	val totalPages: Int
)

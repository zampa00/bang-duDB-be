package it.zampa.bangdudb.repository

import it.zampa.bangdudb.domain.Card
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface CardRepository : CrudRepository<Card, String> {

	@Query("select * from cards")
	fun findCards(): List<Card>

}


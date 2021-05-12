package it.zampa.bangdudb.repository

import it.zampa.bangdudb.domain.Card
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface CardRepository : CrudRepository<Card, String> {

	@Query("select * from cards")
	fun findCards(): List<Card>

	@Query("select * from cards where character_name in (:characters)")
	fun findCards(
		@Param("characters") characters: List<String>
	): List<Card>

	@Query("select * from cards where id = :id")
	fun findCard(
		@Param("id") cardId: String
	): Card?

}


package it.zampa.bangdudb.repository

import it.zampa.bangdudb.domain.Event
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface EventRepository : CrudRepository<Event, String> {

	@Query("select * from events", nativeQuery = true)
	fun findAllEvents(): List<Event>

	@Query("select * from events where id = :id", nativeQuery = true)
	fun findEvent(
		@Param("id") eventId: Int
	): Event?

}
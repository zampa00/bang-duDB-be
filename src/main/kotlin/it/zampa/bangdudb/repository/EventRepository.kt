package it.zampa.bangdudb.repository

import it.zampa.bangdudb.domain.Event
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface EventRepository : CrudRepository<Event, String> {

	@Query("select * from events")
	fun findAllEvents(): List<Event>

	@Query("select * from events where id = :id")
	fun findEvent(
		@Param("id") eventId: Int
	): Event?

}
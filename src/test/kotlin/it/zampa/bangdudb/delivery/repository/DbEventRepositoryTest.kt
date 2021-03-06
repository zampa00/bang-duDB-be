package it.zampa.bangdudb.delivery.repository

import it.zampa.bangdudb.SpringTestParent
import it.zampa.bangdudb.delivery.datamodel.out.EventSummary
import it.zampa.bangdudb.delivery.datamodel.out.Paginated
import it.zampa.bangdudb.domain.Event
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate

@ActiveProfiles("test")
class DbEventRepositoryTest : SpringTestParent() {

	@Autowired
	private lateinit var jdbcTemplate: JdbcTemplate

	@Autowired
	private lateinit var repository: DbEventRepository

	private val jan1st = LocalDate.parse("2021-01-01")
	private val jan2nd = LocalDate.parse("2021-01-02")
	private val jan3rd = LocalDate.parse("2021-01-03")

	@BeforeEach
	fun setUp() {
		jdbcTemplate.update("INSERT INTO public.events (id, name, name_jp, description, description_jp, start_date, end_date, image_hq, stamp, title_point, title_rank, instrument, accessory_point, accessory_rank) VALUES (1, 'name','name_jp','description','description_jp','$jan1st','2021-06-06','image_hq','stamp','title_point','title_rank','instrument','accessory_point','accessory_rank' );")
		jdbcTemplate.update("INSERT INTO public.events (name, name_jp, description, description_jp, start_date, end_date, image_hq, stamp, title_point, title_rank, instrument, accessory_point, accessory_rank) VALUES ('name','name_jp','description','description_jp','$jan1st','2021-06-06','image_hq', 'stamp','title_point','title_rank','instrument','accessory_point','accessory_rank' );")
		jdbcTemplate.update("INSERT INTO public.events (name, name_jp, description, description_jp, start_date, end_date, image_hq, stamp, title_point, title_rank, instrument, accessory_point, accessory_rank) VALUES ('name','name_jp','description','description_jp','$jan1st','2021-06-06','image_hq', 'stamp','title_point','title_rank','instrument','accessory_point','accessory_rank' );")
		jdbcTemplate.update("INSERT INTO public.events (name, name_jp, description, description_jp, start_date, end_date, image_hq, stamp, title_point, title_rank, instrument, accessory_point, accessory_rank) VALUES ('name','name_jp','description','description_jp','$jan1st','2021-06-06','image_hq', 'stamp','title_point','title_rank','instrument','accessory_point','accessory_rank' );")
	}

	@AfterEach
	fun tearDown() {
		jdbcTemplate.update("DELETE FROM events")
	}

	@Test
	fun `retrieve event`() {
		val event = repository.findById(1)
		assertNotNull(event)
		assertEquals(1, event!!.id)
	}

	@Test
	fun `retrieve a paginated summary of all events`() {
		val events: Paginated<EventSummary> = repository.findEventsPaginated(page = 0, resultsPerPage = 3)
		assertEquals(4, events.totalPages)
		assertEquals(3, events.summary.size)
		assertEquals(1, events.summary.first().id)
	}


	@Test
	fun insert() {
		repository.save(EVENT)
		val events = repository.findEventsPaginated(0, 3)
		println(events.summary.first().id)
		assertEquals(5, events.totalPages)
	}

	@Test
	fun `should retrieve a minimal item list`() {
		val list = repository.findEventsForListing()
		assertEquals(4, list.size)
		assertEquals(1, list.first().id)
		assertEquals("name", list.first().name)
	}

	@Test
	fun update() {
		repository.editEvent(EVENT.copy(id = 1, name = "new name"))
		val updatedEvent = repository.findById(1)!!
		assertEquals("new name", updatedEvent.name)
	}

	@Test
	fun `most recent event first`() {
		repository.save(EVENT.copy(name = "2nd", start_date = jan2nd))
		repository.save(EVENT.copy(name = "3rd", start_date = jan3rd))
		val events = repository.findEventsPaginated(0, 3)
		assertEquals("3rd", events.summary.first().name)
	}

	companion object {
		val EVENT = Event(
			id = 10,
			name = "name",
			name_jp = "name_jp",
			description = "description",
			description_jp = "description_jp",
			start_date = LocalDate.now(),
			end_date = LocalDate.now(),
			image_hq = "image_hq",
			stamp = "stamp",
			title_point = "title_point",
			title_rank = "title_rank",
			instrument = "instrument",
			accessory_point = "accessory_point",
			accessory_rank = "accessory_rank",
		)
	}
}
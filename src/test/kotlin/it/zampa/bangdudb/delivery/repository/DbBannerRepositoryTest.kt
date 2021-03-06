package it.zampa.bangdudb.delivery.repository

import it.zampa.bangdudb.SpringTestParent
import it.zampa.bangdudb.delivery.datamodel.out.BannerSummary
import it.zampa.bangdudb.delivery.datamodel.out.Paginated
import it.zampa.bangdudb.domain.Banner
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
class DbBannerRepositoryTest : SpringTestParent() {

	@Autowired
	private lateinit var jdbcTemplate: JdbcTemplate

	@Autowired
	private lateinit var repository: DbBannerRepository

	private val jan1st = LocalDate.parse("2021-01-01")
	private val jan2nd = LocalDate.parse("2021-01-02")
	private val jan3rd = LocalDate.parse("2021-01-03")

	@BeforeEach
	fun setUp() {
		jdbcTemplate.update("INSERT INTO public.banners (id, name, name_jp, description, description_jp, start_date, end_date, image_hq) VALUES (1, 'name','name_jp','description','description_jp','$jan1st','2021-06-06','image_hq' );")
		jdbcTemplate.update("INSERT INTO public.banners (name, name_jp, description, description_jp, start_date, end_date, image_hq) VALUES ('name','name_jp','description','description_jp','$jan1st','2021-06-06','image_hq' );")
		jdbcTemplate.update("INSERT INTO public.banners (name, name_jp, description, description_jp, start_date, end_date, image_hq) VALUES ('name','name_jp','description','description_jp','$jan1st','2021-06-06','image_hq' );")
		jdbcTemplate.update("INSERT INTO public.banners (name, name_jp, description, description_jp, start_date, end_date, image_hq) VALUES ('name','name_jp','description','description_jp','$jan1st','2021-06-06','image_hq' );")
	}

	@AfterEach
	fun tearDown() {
		jdbcTemplate.update("DELETE FROM banners")
	}

	@Test
	fun `retrieve banner`() {
		val banner = repository.findById(1)
		assertNotNull(banner)
		assertEquals(1, banner!!.id)
	}

	@Test
	fun `retrieve a paginated summary of all banners`() {
		val banners: Paginated<BannerSummary> = repository.findBannersPaginated(page = 0, resultsPerPage = 3)
		assertEquals(4, banners.totalPages)
		assertEquals(3, banners.summary.size)
		assertEquals(1, banners.summary.first().id)
	}

	@Test
	fun `most recent banner first`() {
		repository.save(BANNER.copy(name = "2nd", start_date = jan2nd))
		repository.save(BANNER.copy(name = "3rd", start_date = jan3rd))
		val events = repository.findBannersPaginated(0, 3)
		assertEquals("3rd", events.summary.first().name)
	}

	@Test
	fun insert() {
		repository.save(BANNER)
		val banners = repository.findBannersPaginated(0, 3)
		println(banners.summary.first().id)
		assertEquals(5, banners.totalPages)
	}

	@Test
	fun update() {
		repository.editBanner(BANNER.copy(id = 1, name = "new name"))
		val updatedBanner = repository.findById(1)!!
		assertEquals("new name", updatedBanner.name)
	}

	companion object {
		val BANNER = Banner(
			id = 10,
			name = "name",
			name_jp = "name_jp",
			description = "description",
			description_jp = "description_jp",
			start_date = LocalDate.now(),
			end_date = LocalDate.now(),
			image_hq = "image_hq"
		)
	}
}
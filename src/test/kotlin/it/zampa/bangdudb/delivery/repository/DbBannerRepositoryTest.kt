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

	@BeforeEach
	fun setUp() {
		jdbcTemplate.update("INSERT INTO public.banners (id, name, name_jp, description, description_jp, start_date, end_date, image_hq) VALUES (1, 'name','name_jp','description','description_jp','2021-06-01','2021-06-06','image_hq' );")
		jdbcTemplate.update("INSERT INTO public.banners (name, name_jp, description, description_jp, start_date, end_date, image_hq) VALUES ('name','name_jp','description','description_jp','2021-06-01','2021-06-06','image_hq' );")
		jdbcTemplate.update("INSERT INTO public.banners (name, name_jp, description, description_jp, start_date, end_date, image_hq) VALUES ('name','name_jp','description','description_jp','2021-06-01','2021-06-06','image_hq' );")
		jdbcTemplate.update("INSERT INTO public.banners (name, name_jp, description, description_jp, start_date, end_date, image_hq) VALUES ('name','name_jp','description','description_jp','2021-06-01','2021-06-06','image_hq' );")
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
	fun insert() {
		repository.save(BANNER)
		val banners = repository.findBannersPaginated(0, 3)
		println(banners.summary.first().id)
		assertEquals(5, banners.totalPages)
	}

	companion object {
		val BANNER = Banner(
			id = 10,
			name = "",
			name_jp = "",
			description = "",
			description_jp = "",
			start_date = LocalDate.now(),
			end_date = LocalDate.now(),
			image_hq = ""
		)
	}
}
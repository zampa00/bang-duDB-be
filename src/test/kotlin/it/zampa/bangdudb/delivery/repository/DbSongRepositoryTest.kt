package it.zampa.bangdudb.delivery.repository

import it.zampa.bangdudb.SpringTestParent
import it.zampa.bangdudb.delivery.datamodel.out.Paginated
import it.zampa.bangdudb.domain.Song
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate

@ActiveProfiles("test")
class DbSongRepositoryTest : SpringTestParent() {

	@Autowired
	private lateinit var jdbcTemplate: JdbcTemplate

	@Autowired
	private lateinit var repository: DbSongRepository

	@BeforeEach
	fun setUp() {
		jdbcTemplate.update("INSERT INTO public.songs (name, name_jp, band, is_cover, release_date, image) VALUES ('song1', 'jp', 'argonavis', true, '2021-01-01', 'image');")
		jdbcTemplate.update("INSERT INTO public.songs (name, name_jp, band, is_cover, release_date, image) VALUES ('song2', 'jp', 'argonavis', false, '2021-02-01', 'image');")
		jdbcTemplate.update("INSERT INTO public.songs (name, name_jp, band, is_cover, release_date, image) VALUES ('song3', 'jp', 'cyaron', true, '2021-03-01', 'image');")
		jdbcTemplate.update("INSERT INTO public.songs (name, name_jp, band, is_cover, release_date, image) VALUES ('song4', 'jp', 'azalea', true, '2021-04-01', 'image');")
	}

	@AfterEach
	fun tearDown() {
		jdbcTemplate.update("DELETE FROM songs")
	}

	@Test
	fun `retrieve a paginated summary of all cards`() {
		val songs: Paginated<Song> = repository.findSongsPaginated(page = 0, resultsPerPage = 3)
		assertEquals(4, songs.totalPages)
		assertEquals(3, songs.summary.size)
		assertEquals("song1", songs.summary.first().name)
	}

	@Test
	fun `retrieve a paginated summary after single band filter`() {
		val songs: Paginated<Song> = repository.findSongsPaginatedFilteredBy(page = 0, resultsPerPage = 3, bands = listOf("argonavis"))
		assertEquals(2, songs.totalPages)
		assertEquals(2, songs.summary.size)
		assertEquals("song1", songs.summary.first().name)
	}

	@Test
	fun `retrieve a paginated summary after two band filter`() {
		val songs: Paginated<Song> = repository.findSongsPaginatedFilteredBy(page = 0, resultsPerPage = 3, bands = listOf("argonavis", "cyaron"))
		assertEquals(3, songs.totalPages)
		assertEquals(3, songs.summary.size)
		assertEquals("song1", songs.summary.first().name)
	}

	@Test
	fun `retrieve a paginate summary for a boolean`() {
		val cards: Paginated<Song> = repository.findSongsPaginatedFilteredBy(page = 0, resultsPerPage = 3, is_cover = false)
		assertEquals(1, cards.totalPages)
		assertEquals(1, cards.summary.size)
		assertEquals("song2", cards.summary.first().name)
	}

	@Test
	fun insert() {
		repository.save(SONG)
		val songs = repository.findSongsPaginated(0, 3)
		assertEquals(5, songs.totalPages)
	}

	companion object {
		val SONG = Song(
			name = "song5",
			name_jp = "jp",
			band = "guilty kiss",
			is_cover = false,
			release_date = LocalDate.now(),
			image = ""
		)
	}
}
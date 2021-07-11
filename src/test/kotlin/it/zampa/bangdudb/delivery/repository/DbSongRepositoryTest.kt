package it.zampa.bangdudb.delivery.repository

import it.zampa.bangdudb.SpringTestParent
import it.zampa.bangdudb.domain.Song
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
class DbSongRepositoryTest : SpringTestParent() {

	@Autowired
	private lateinit var jdbcTemplate: JdbcTemplate

	@Autowired
	private lateinit var repository: DbSongRepository

	private val jan1st = LocalDate.parse("2021-01-01")
	private val jan2nd = LocalDate.parse("2021-01-02")
	private val jan3rd = LocalDate.parse("2021-01-03")

	@BeforeEach
	fun setUp() {
		jdbcTemplate.update("INSERT INTO public.songs (id, name, name_jp, band, lyricist, composer, arranger, difficulty, other_info, is_cover, release_date, image) VALUES (1, 'song1', 'jp', 'argonavis', '', '', '', '', '', true, '$jan1st', 'image');")
		jdbcTemplate.update("INSERT INTO public.songs (name, name_jp, band, lyricist, composer, arranger, difficulty, other_info, is_cover, release_date, image) VALUES ('song2', 'jp', 'argonavis', '', '', '', '', '', false, '$jan1st', 'image');")
		jdbcTemplate.update("INSERT INTO public.songs (name, name_jp, band, lyricist, composer, arranger, difficulty, other_info, is_cover, release_date, image) VALUES ('song3', 'jp', 'cyaron', '', '', '', '', '', true, '$jan1st', 'image');")
		jdbcTemplate.update("INSERT INTO public.songs (name, name_jp, band, lyricist, composer, arranger, difficulty, other_info, is_cover, release_date, image) VALUES ('song4', 'jp', 'azalea', '', '', '', '', '', true, '$jan1st', 'image');")
	}

	@AfterEach
	fun tearDown() {
		jdbcTemplate.update("DELETE FROM songs")
	}

	@Test
	fun `retrieve a song from id`() {
		val song = repository.findById(1)
		assertNotNull(song)
		assertEquals("song1", song?.name)
	}

	@Test
	fun `retrieve a paginated summary of all cards`() {
		val songs = repository.findSongsPaginated(page = 0, resultsPerPage = 3)
		assertEquals(4, songs.totalPages)
		assertEquals(3, songs.summary.size)
		assertEquals("song1", songs.summary.first().name)
	}

	@Test
	fun `retrieve a paginated summary after single band filter`() {
		val songs = repository.findSongsPaginatedFilteredBy(page = 0, resultsPerPage = 3, bands = listOf("argonavis"))
		assertEquals(2, songs.totalPages)
		assertEquals(2, songs.summary.size)
		assertEquals("song1", songs.summary.first().name)
	}

	@Test
	fun `retrieve a paginated summary after two band filter`() {
		val songs = repository.findSongsPaginatedFilteredBy(page = 0, resultsPerPage = 3, bands = listOf("argonavis", "cyaron"))
		assertEquals(3, songs.totalPages)
		assertEquals(3, songs.summary.size)
		assertEquals("song1", songs.summary.first().name)
	}

	@Test
	fun `retrieve a paginate summary for a boolean`() {
		val cards = repository.findSongsPaginatedFilteredBy(page = 0, resultsPerPage = 3, is_cover = false)
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

	@Test
	fun `most recent songs are first`() {
		repository.save(SONG.copy(name = "2nd", release_date = jan2nd))
		repository.save(SONG.copy(name = "3rd", release_date = jan3rd))
		val songs = repository.findSongsPaginated(0, 3)
		assertEquals("3rd", songs.summary.first().name)
	}

	companion object {
		val SONG = Song(
			name = "song5",
			name_jp = "jp",
			band = "guilty kiss",
			lyricist = "",
			composer = "",
			arranger = "",
			difficulty = "",
			other_info = "",
			is_cover = false,
			release_date = LocalDate.now(),
			image = ""
		)
	}
}
package it.zampa.bangdudb.delivery.repository

import it.zampa.bangdudb.SpringTestParent
import it.zampa.bangdudb.delivery.datamodel.out.CardSummary
import it.zampa.bangdudb.delivery.datamodel.out.Paginated
import it.zampa.bangdudb.domain.Card
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
class DbCardRepositoryTest : SpringTestParent() {

	@Autowired
	private lateinit var jdbcTemplate: JdbcTemplate

	@Autowired
	private lateinit var repository: DbCardRepository

	@BeforeEach
	fun setUp() {
		jdbcTemplate.update("INSERT INTO public.cards (id, attribute, band, banner_id, card_name, card_name_jp, character_name, event_id, is_birthday, is_event, is_gacha, is_promo, is_unavailable_gacha, pf, power, rarity, release_date, skill_dailylife_description, skill_dailylife_description_jp, skill_dailylife_name, skill_dailylife_name_jp, skill_session_description, skill_session_description_jp, skill_session_name, skill_session_name_jp, skill_session_type, src_base_hq, src_base_lq, src_idl_hq, src_idl_lq, tec, vi, src_avatar) VALUES ('124_0001', 'Cool', 'Fantôme Iris', null, '1', '1', 'FELIX', null, true, false, false, false, false, 2, 1, 4, '2021-05-30', '1', '1', '1', '1', '1', '1', '1', '1', 'scorer', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_1.png', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_1_lq.jpg', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_2.png', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_2_lq.jpg', 3, 4, '');")
		jdbcTemplate.update("INSERT INTO public.cards (id, attribute, band, banner_id, card_name, card_name_jp, character_name, event_id, is_birthday, is_event, is_gacha, is_promo, is_unavailable_gacha, pf, power, rarity, release_date, skill_dailylife_description, skill_dailylife_description_jp, skill_dailylife_name, skill_dailylife_name_jp, skill_session_description, skill_session_description_jp, skill_session_name, skill_session_name_jp, skill_session_type, src_base_hq, src_base_lq, src_idl_hq, src_idl_lq, tec, vi, src_avatar) VALUES ('124_0002', 'Cool', 'Fantôme Iris', null, '1', '1', 'FELIX', null, true, false, false, false, false, 2, 1, 4, '2021-05-30', '1', '1', '1', '1', '1', '1', '1', '1', 'scorer', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_1.png', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_1_lq.jpg', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_2.png', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_2_lq.jpg', 3, 4, '');")
		jdbcTemplate.update("INSERT INTO public.cards (id, attribute, band, banner_id, card_name, card_name_jp, character_name, event_id, is_birthday, is_event, is_gacha, is_promo, is_unavailable_gacha, pf, power, rarity, release_date, skill_dailylife_description, skill_dailylife_description_jp, skill_dailylife_name, skill_dailylife_name_jp, skill_session_description, skill_session_description_jp, skill_session_name, skill_session_name_jp, skill_session_type, src_base_hq, src_base_lq, src_idl_hq, src_idl_lq, tec, vi, src_avatar) VALUES ('124_0003', 'Cool', 'Argonavis', null, '1', '1', 'Ren', null, true, false, false, false, false, 2, 1, 4, '2021-05-30', '1', '1', '1', '1', '1', '1', '1', '1', 'scorer', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_1.png', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_1_lq.jpg', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_2.png', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_2_lq.jpg', 3, 4, '');")
		jdbcTemplate.update("INSERT INTO public.cards (id, attribute, band, banner_id, card_name, card_name_jp, character_name, event_id, is_birthday, is_event, is_gacha, is_promo, is_unavailable_gacha, pf, power, rarity, release_date, skill_dailylife_description, skill_dailylife_description_jp, skill_dailylife_name, skill_dailylife_name_jp, skill_session_description, skill_session_description_jp, skill_session_name, skill_session_name_jp, skill_session_type, src_base_hq, src_base_lq, src_idl_hq, src_idl_lq, tec, vi, src_avatar) VALUES ('124_0004', 'Cool', 'Argonavis', null, '1', '1', 'Banri', null, true, false, false, false, false, 2, 1, 4, '2021-05-30', '1', '1', '1', '1', '1', '1', '1', '1', 'scorer', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_1.png', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_1_lq.jpg', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_2.png', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_2_lq.jpg', 3, 4, '');")
	}

	@AfterEach
	fun tearDown() {
		jdbcTemplate.update("DELETE FROM cards")
	}

	@Test
	fun `retrieve card`() {
		val card = repository.findById("124_0001")
		assertNotNull(card)
		assertEquals("124_0001", card!!.id)
	}

	@Test
	fun `retrieve a paginated summary of all cards`() {
		val cards: Paginated<CardSummary> = repository.findCardsPaginated(page = 0, resultsPerPage = 3)
		assertEquals(4, cards.totalPages)
		assertEquals(3, cards.summary.size)
		assertEquals("124_0001", cards.summary.first().card_id)
	}

	@Test
	fun `retrieve a paginated summary after single character filter`() {
		val cards: Paginated<CardSummary> = repository.findCardsPaginatedFilteredBy(page = 0, resultsPerPage = 3, characters = listOf("FELIX"))
		assertEquals(2, cards.totalPages)
		assertEquals(2, cards.summary.size)
		assertEquals("124_0001", cards.summary.first().card_id)
	}

	@Test
	fun `retrieve a paginated summary after two character filter`() {
		val cards: Paginated<CardSummary> = repository.findCardsPaginatedFilteredBy(page = 0, resultsPerPage = 3, characters = listOf("FELIX", "Ren"))
		assertEquals(3, cards.totalPages)
		assertEquals(3, cards.summary.size)
		assertEquals("124_0001", cards.summary.first().card_id)
	}

	@Test
	fun `retrieve a paginate summary for band`() {
		val cards: Paginated<CardSummary> = repository.findCardsPaginatedFilteredBy(page = 0, resultsPerPage = 3, bands = listOf("Fantôme Iris"))
		assertEquals(2, cards.totalPages)
		assertEquals(2, cards.summary.size)
		assertEquals("124_0001", cards.summary.first().card_id)
	}

	@Test
	fun `retrieve a paginate summary for rarities`() {
		val cards: Paginated<CardSummary> = repository.findCardsPaginatedFilteredBy(page = 0, resultsPerPage = 3, rarities = listOf(4))
		assertEquals(4, cards.totalPages)
		assertEquals(3, cards.summary.size)
		assertEquals("124_0001", cards.summary.first().card_id)
	}

	@Test
	fun `retrieve a paginate summary for a boolean`() {
		val cards: Paginated<CardSummary> = repository.findCardsPaginatedFilteredBy(page = 0, resultsPerPage = 3, is_birthday = true)
		assertEquals(4, cards.totalPages)
		assertEquals(3, cards.summary.size)
		assertEquals("124_0001", cards.summary.first().card_id)
	}

	@Test
	fun insert() {
		repository.save(CARD)
		val cards = repository.findCardsPaginated(0, 3)
		assertEquals(5, cards.totalPages)
	}

	companion object {
		val CARD = Card(
			id = "014_0001",
			banner_id = null,
			event_id = null,
			character_name = "Nanahoshi Ren",
			band = "Argonavis",
			card_name = "card name",
			card_name_jp = "card name jp",
			rarity = 4,
			attribute = "cool",
			release_date = LocalDate.parse("2021-01-21")!!,
			power = 1,
			pf = 1,
			tec = 1,
			vi = 1,
			skill_session_name = "skill session name",
			skill_session_name_jp = "skill session name jp",
			skill_session_description = "skill session description",
			skill_session_description_jp = "skill session description jp",
			skill_session_type = "scorer",
			skill_dailylife_name = "skill daily life name",
			skill_dailylife_name_jp = "skill daily life name jp",
			skill_dailylife_description = "skill daily life description",
			skill_dailylife_description_jp = "skill daily life description jp",
			is_gacha = false,
			is_unavailable_gacha = false,
			is_event = false,
			is_birthday = false,
			is_promo = false,
			src_base_lq = "001_0001_1_lq.jpg",
			src_idl_lq = "001_0001_2_lq.jpg",
			src_base_hq = "001_0001_1.png",
			src_idl_hq = "001_0001_2.png",
			src_avatar = ""
		)
	}
}
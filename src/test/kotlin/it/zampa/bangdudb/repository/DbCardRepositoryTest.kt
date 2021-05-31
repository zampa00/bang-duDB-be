package it.zampa.bangdudb.repository

import it.zampa.bangdudb.SpringTestParent
import it.zampa.bangdudb.delivery.datamodel.PaginatedCards
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DbCardRepositoryTest : SpringTestParent() {

	@Autowired
	private lateinit var jdbcTemplate: JdbcTemplate

	@Autowired
	private lateinit var repository: DbCardRepository

	@BeforeAll
	fun setUp() {
		jdbcTemplate.update("INSERT INTO public.cards (id, attribute, band, banner_id, card_id, card_name, card_name_jp, character_name, event_id, is_birthday, is_event, is_gacha, is_promo, is_unavailable_gacha, pf, power, rarity, release_date, skill_dailylife_description, skill_dailylife_description_jp, skill_dailylife_name, skill_dailylife_name_jp, skill_session_description, skill_session_description_jp, skill_session_name, skill_session_name_jp, skill_session_type, src_base_hq, src_base_lq, src_idl_hq, src_idl_lq, tec, vi) VALUES (1, 'Cool', 'Fantôme Iris', null, '124_0001', '1', '1', 'FELIX', null, true, false, false, false, false, 2, 1, 4, '2021-05-30', '1', '1', '1', '1', '1', '1', '1', '1', 'scorer', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_1.png', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_1_lq.jpg', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_2.png', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_2_lq.jpg', 3, 4);")
		jdbcTemplate.update("INSERT INTO public.cards (id, attribute, band, banner_id, card_id, card_name, card_name_jp, character_name, event_id, is_birthday, is_event, is_gacha, is_promo, is_unavailable_gacha, pf, power, rarity, release_date, skill_dailylife_description, skill_dailylife_description_jp, skill_dailylife_name, skill_dailylife_name_jp, skill_session_description, skill_session_description_jp, skill_session_name, skill_session_name_jp, skill_session_type, src_base_hq, src_base_lq, src_idl_hq, src_idl_lq, tec, vi) VALUES (2, 'Cool', 'Fantôme Iris', null, '124_0002', '1', '1', 'FELIX', null, true, false, false, false, false, 2, 1, 4, '2021-05-30', '1', '1', '1', '1', '1', '1', '1', '1', 'scorer', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_1.png', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_1_lq.jpg', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_2.png', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_2_lq.jpg', 3, 4);")
		jdbcTemplate.update("INSERT INTO public.cards (id, attribute, band, banner_id, card_id, card_name, card_name_jp, character_name, event_id, is_birthday, is_event, is_gacha, is_promo, is_unavailable_gacha, pf, power, rarity, release_date, skill_dailylife_description, skill_dailylife_description_jp, skill_dailylife_name, skill_dailylife_name_jp, skill_session_description, skill_session_description_jp, skill_session_name, skill_session_name_jp, skill_session_type, src_base_hq, src_base_lq, src_idl_hq, src_idl_lq, tec, vi) VALUES (3, 'Cool', 'Argonavis', null, '124_0003', '1', '1', 'Ren', null, true, false, false, false, false, 2, 1, 4, '2021-05-30', '1', '1', '1', '1', '1', '1', '1', '1', 'scorer', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_1.png', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_1_lq.jpg', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_2.png', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_2_lq.jpg', 3, 4);")
		jdbcTemplate.update("INSERT INTO public.cards (id, attribute, band, banner_id, card_id, card_name, card_name_jp, character_name, event_id, is_birthday, is_event, is_gacha, is_promo, is_unavailable_gacha, pf, power, rarity, release_date, skill_dailylife_description, skill_dailylife_description_jp, skill_dailylife_name, skill_dailylife_name_jp, skill_session_description, skill_session_description_jp, skill_session_name, skill_session_name_jp, skill_session_type, src_base_hq, src_base_lq, src_idl_hq, src_idl_lq, tec, vi) VALUES (4, 'Cool', 'Argonavis', null, '124_0004', '1', '1', 'Banri', null, true, false, false, false, false, 2, 1, 4, '2021-05-30', '1', '1', '1', '1', '1', '1', '1', '1', 'scorer', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_1.png', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_1_lq.jpg', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_2.png', 'https://bang-dudb-test.s3-eu-west-1.amazonaws.com/cards/Card_Pic_124_0002_2_lq.jpg', 3, 4);")
	}

	@AfterAll
	fun tearDown() {
		jdbcTemplate.update("DELETE FROM cards")
	}

	@Test
	fun `retrieve card`() {
		val card = repository.findById("124_0001")
		assertNotNull(card)
		assertEquals("124_0001", card!!.card_id)
	}

	@Test
	fun `retrieve a paginated summary of all cards`() {
		val cards: PaginatedCards = repository.findCardsPaginated(page = 0, resultsPerPage = 3)
		assertEquals(4, cards.totalPages)
		assertEquals(3, cards.cardSummary.size)
		assertEquals("124_0001", cards.cardSummary.first().card_id)
	}

	@Test
	fun `retrieve a paginated summary after single character filter`() {
		val cards: PaginatedCards = repository.findCardsPaginatedFilteredBy(page = 0, resultsPerPage = 3, characters = "FELIX")
		assertEquals(2, cards.totalPages)
		assertEquals(2, cards.cardSummary.size)
		assertEquals("124_0001", cards.cardSummary.first().card_id)
	}
}
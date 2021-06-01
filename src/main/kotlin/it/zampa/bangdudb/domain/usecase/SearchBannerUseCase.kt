package it.zampa.bangdudb.domain.usecase

import it.zampa.bangdudb.delivery.datamodel.out.BannerWithCards
import it.zampa.bangdudb.delivery.datamodel.out.CardSummary
import it.zampa.bangdudb.domain.Banner
import it.zampa.bangdudb.domain.repository.BannerRepository
import it.zampa.bangdudb.domain.repository.CardRepository

class SearchBannerUseCase(
	val cardRepository: CardRepository,
	val bannerRepository: BannerRepository
) {

	fun search(bannerId: Int): BannerWithCards {
		val banner: Banner? = bannerRepository.findById(bannerId)
		val cards: List<CardSummary>? = banner?.id?.let { cardRepository.findCardsFromEvent(it) }
		return BannerWithCards(
			banner = banner,
			cards = cards
		)
	}

}
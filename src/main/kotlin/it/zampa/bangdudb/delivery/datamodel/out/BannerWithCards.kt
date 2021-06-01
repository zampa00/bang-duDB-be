package it.zampa.bangdudb.delivery.datamodel.out

import it.zampa.bangdudb.domain.Banner

data class BannerWithCards(
	val banner: Banner?,
	val cards: List<CardSummary>?
)

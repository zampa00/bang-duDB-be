package it.zampa.bangdudb.domain.repository

import it.zampa.bangdudb.delivery.datamodel.out.BannerSummary
import it.zampa.bangdudb.delivery.datamodel.out.ListItem
import it.zampa.bangdudb.delivery.datamodel.out.Paginated
import it.zampa.bangdudb.domain.Banner

interface BannerRepository {
	fun findById(bannerId: Int): Banner?
	fun findBannersForListing(): List<ListItem>
	fun findBannersPaginated(page: Int, resultsPerPage: Int): Paginated<BannerSummary>
	fun save(banner: Banner)
}
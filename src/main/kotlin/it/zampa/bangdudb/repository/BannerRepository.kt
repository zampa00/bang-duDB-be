package it.zampa.bangdudb.repository

import it.zampa.bangdudb.domain.Banner
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BannerRepository : CrudRepository<Banner, String> {

	@Query("select * from banners", nativeQuery = true)
	fun findAllBanners(): List<Banner>

	@Query("select * from banners where id = :id", nativeQuery = true)
	fun findBanner(
		@Param("id") bannerId: Int
	): Banner?

}
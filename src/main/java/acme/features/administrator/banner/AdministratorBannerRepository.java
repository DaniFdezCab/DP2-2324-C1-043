
package acme.features.administrator.banner;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.banners.Banner;

public interface AdministratorBannerRepository extends AbstractRepository {

	@Query("select b from Banner b")
	Collection<Banner> findAllBanners();

	@Query("select b from Banner b where b.id = :id")
	Banner findOneBannerById(int id);
}

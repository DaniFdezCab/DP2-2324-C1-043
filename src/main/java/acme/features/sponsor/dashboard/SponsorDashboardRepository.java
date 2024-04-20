
package acme.features.sponsor.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	@Query("SELECT COUNT(i) FROM Invoice i WHERE i.tax <= 21.00")
	Integer countInvoicesWithTaxLessThanOrEqualTo21();

	@Query("SELECT COUNT(s) FROM Sponsorship s WHERE s.moreInfo IS NOT NULL")
	Integer countLinkedSponsorships();

	@Query("SELECT AVG(s.amount.amount) FROM Sponsorship s  ")
	Double averageSponsorshipAmount();

	@Query("SELECT STDDEV(s.amount.amount) FROM Sponsorship s ")
	Double deviationSponsorshipAmount();

	@Query("SELECT MIN(s.amount.amount) FROM Sponsorship s ")
	Double minSponsorshipAmount();

	@Query("SELECT MAX(s.amount.amount) FROM Sponsorship s  ")
	Double maxSponsorshipAmount();

	@Query("SELECT AVG(i.quantity.amount) FROM Invoice i ")
	Double averageInvoiceAmount();

	@Query("SELECT STDDEV(i.quantity.amount) FROM Invoice i ")
	Double deviationInvoiceAmount();

	@Query("SELECT MIN(i.quantity.amount) FROM Invoice i")
	Double minInvoiceAmount();

	@Query("SELECT MAX(i.quantity.amount) FROM Invoice i ")
	Double maxInvoiceAmount();
}

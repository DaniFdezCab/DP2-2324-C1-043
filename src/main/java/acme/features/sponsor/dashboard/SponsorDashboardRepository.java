
package acme.features.sponsor.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	@Query("SELECT COUNT(i) FROM Invoice i WHERE i.tax <= 21.00 AND i.sponsorship.sponsor.id =: id")
	Integer countInvoicesWithTaxLessThanOrEqualTo21(int id);

	@Query("SELECT COUNT(s) FROM Sponsorship s WHERE s.moreInfo IS NOT NULL AND s.sponsor.id =: id")
	Integer countLinkedSponsorships(int id);

	@Query("SELECT AVG(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id =: id")
	Double averageSponsorshipAmount(int id);

	@Query("SELECT STDDEV(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id =: id")
	Double deviationSponsorshipAmount(int id);

	@Query("SELECT MIN(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id =: id")
	Double minSponsorshipAmount(int id);

	@Query("SELECT MAX(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id =: id")
	Double maxSponsorshipAmount(int id);

	@Query("SELECT AVG(i.quantity.amount) FROM Invoice i WHERE i.sponsorship.sponsor.id =: id")
	Double averageInvoiceAmount(int id);

	@Query("SELECT STDDEV(i.quantity.amount) FROM Invoice i WHERE i.sponsorship.sponsor.id =: id ")
	Double deviationInvoiceAmount(int id);

	@Query("SELECT MIN(i.quantity.amount) FROM Invoice i WHERE i.sponsorship.sponsor.id =: id")
	Double minInvoiceAmount(int id);

	@Query("SELECT MAX(i.quantity.amount) FROM Invoice i WHERE i.sponsorship.sponsor.id =: id")
	Double maxInvoiceAmount(int id);
}

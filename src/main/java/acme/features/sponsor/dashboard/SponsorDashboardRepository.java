
package acme.features.sponsor.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.datatypes.Money;
import acme.client.repositories.AbstractRepository;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	@Query("SELECT COUNT(i) FROM Invoice i WHERE i.tax <= 0.21 AND i.sponsorship.sponsor.id =:id")
	Integer countInvoicesWithTaxLessThanOrEqualTo21(int id);

	@Query("SELECT COUNT(s) FROM Sponsorship s WHERE s.moreInfo IS NOT NULL AND s.sponsor.id =:id")
	Integer countLinkedSponsorships(int id);

	@Query("SELECT s.amount FROM Sponsorship s where s.sponsor.id=:id")
	Collection<Money> sponsorshipAmounts(int id);

	@Query("SELECT i.quantity FROM Invoice i where i.sponsorship.sponsor.id=:id")
	Collection<Money> invoiceQuantities(int id);
}

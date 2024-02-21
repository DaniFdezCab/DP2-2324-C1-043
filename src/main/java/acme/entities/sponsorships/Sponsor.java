
package acme.entities.sponsorships;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Sponsor extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@OneToMany(mappedBy = "sponsor")
	private List<Invoice>		invoices;

	@OneToMany(mappedBy = "sponsor")
	private List<Sponsorship>	sponsorships;


	public int invoicesTaxed() {
		return (int) this.invoices.stream().filter(i -> i.getTax() <= 21.00).count();
	}

	public int getTotalSponsorshipsWithLink() {
		return (int) this.sponsorships.stream().filter(sponsorship -> sponsorship.getOptionalLink() != null && !sponsorship.getOptionalLink().isEmpty()).count();
	}

	public double getAverageAmountOfSponsorships() {
		return this.sponsorships.stream().mapToDouble(s -> s.getAmount()).average().orElse(0);
	}

	public double getStandardDeviationOfSponsorshipAmounts() {
		double average = this.getAverageAmountOfSponsorships();
		double variance = this.sponsorships.stream().mapToDouble(sponsorship -> Math.pow(sponsorship.getAmount() - average, 2)).average().orElse(0.0);
		return Math.sqrt(variance);
	}

	public double getMinimumAmountOfSponsorships() {
		return this.sponsorships.stream().mapToDouble(Sponsorship::getAmount).min().orElse(0.0);
	}

	public double getMaximumAmountOfSponsorships() {
		return this.sponsorships.stream().mapToDouble(Sponsorship::getAmount).max().orElse(0.0);
	}

	public double getAverageQuantityOfInvoices() {
		return this.invoices.stream().mapToDouble(Invoice::getQuantity).average().orElse(0.0);
	}

	public double getStandardDeviationOfInvoiceQuantities() {
		double average = this.getAverageQuantityOfInvoices();
		double variance = this.invoices.stream().mapToDouble(invoice -> Math.pow(invoice.getQuantity() - average, 2)).average().orElse(0.0);
		return Math.sqrt(variance);
	}

	public int getMinimumQuantityOfInvoices() {
		return this.invoices.stream().mapToInt(Invoice::getQuantity).min().orElse(0);
	}

	public int getMaximumQuantityOfInvoices() {
		return this.invoices.stream().mapToInt(Invoice::getQuantity).max().orElse(0);
	}

}

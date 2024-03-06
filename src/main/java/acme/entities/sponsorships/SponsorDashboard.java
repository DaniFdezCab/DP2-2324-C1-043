
package acme.entities.sponsorships;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SponsorDashboard extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@OneToMany
	private List<Invoice>		invoices;

	@OneToMany
	private List<Sponsorship>	sponsorships;


	public int invoicesTaxed() {
		return (int) this.invoices.stream().filter(i -> i.getTax() <= 21.00).count();
	}

	public int getTotalSponsorshipsWithLink() {
		return (int) this.sponsorships.stream().filter(sponsorship -> sponsorship.getMoreInfo() != null && !sponsorship.getMoreInfo().isEmpty()).count();
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(this.invoices, this.sponsorships);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		SponsorDashboard other = (SponsorDashboard) obj;
		return Objects.equals(this.invoices, other.invoices) && Objects.equals(this.sponsorships, other.sponsorships);
	}

}

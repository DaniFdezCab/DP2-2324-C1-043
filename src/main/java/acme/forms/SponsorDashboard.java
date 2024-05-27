
package acme.forms;

import acme.client.data.AbstractForm;
import acme.client.data.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	/**
	 *
	 */
	private static final long	serialVersionUID	= 1L;

	private Integer				taxedInvoices;

	private Integer				linkedSponsorships;

	private Money				averageSponsorship;

	private Money				averageInvoice;

	private Money				deviationInvoice;

	private Money				deviationSponsorship;

	private Money				minimumInvoice;

	private Money				minimumSponsorship;

	private Money				maximumInvoice;

	private Money				maximumSponsorship;

}

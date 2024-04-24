
package acme.features.sponsor.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.SponsorDashboard;
import acme.roles.Sponsor;

@Service
public class SponsorDashboardShowService extends AbstractService<Sponsor, SponsorDashboard> {

	@Autowired
	private SponsorDashboardRepository repo;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Integer sponsorId = super.getRequest().getPrincipal().getActiveRoleId();

		SponsorDashboard sponsorDashboard;

		Integer taxedInvoices;

		Integer linkedSponsorships;

		Double averageSponsorship;

		Double averageInvoice;

		Double maximumInvoice;

		Double maximumSponsorship;

		Double minimumInvoice;

		Double minimumSponsorship;

		Double deviationInvoice;

		Double deviationSponsorship;

		taxedInvoices = this.repo.countInvoicesWithTaxLessThanOrEqualTo21(sponsorId);

		linkedSponsorships = this.repo.countLinkedSponsorships(sponsorId);

		averageSponsorship = this.repo.averageSponsorshipAmount(sponsorId);

		averageInvoice = this.repo.averageInvoiceAmount(sponsorId);

		maximumInvoice = this.repo.maxInvoiceAmount(sponsorId);

		maximumSponsorship = this.repo.maxSponsorshipAmount(sponsorId);

		minimumInvoice = this.repo.minInvoiceAmount(sponsorId);

		minimumSponsorship = this.repo.minSponsorshipAmount(sponsorId);

		deviationInvoice = this.repo.deviationInvoiceAmount(sponsorId);

		deviationSponsorship = this.repo.deviationSponsorshipAmount(sponsorId);
		sponsorDashboard = new SponsorDashboard();

		sponsorDashboard.setTaxedInvoices(taxedInvoices);

		sponsorDashboard.setLinkedSponsorships(linkedSponsorships);

		Money averageMoney = new Money();
		averageMoney.setAmount(averageSponsorship);
		averageMoney.setCurrency("EUR");
		sponsorDashboard.setAverageSponsorship(averageMoney);

		Money averageMoneyI = new Money();
		averageMoneyI.setAmount(averageInvoice);
		averageMoneyI.setCurrency("EUR");
		sponsorDashboard.setAverageInvoice(averageMoneyI);

		Money maximumMoneyI = new Money();
		maximumMoneyI.setAmount(maximumInvoice);
		maximumMoneyI.setCurrency("EUR");
		sponsorDashboard.setMaximumInvoice(maximumMoneyI);

		Money maximumMoneyS = new Money();
		maximumMoneyS.setAmount(maximumSponsorship);
		maximumMoneyS.setCurrency("EUR");
		sponsorDashboard.setMaximumSponsorship(maximumMoneyS);

		Money minimumMoneyS = new Money();
		minimumMoneyS.setAmount(minimumSponsorship);
		minimumMoneyS.setCurrency("EUR");
		sponsorDashboard.setMinimumSponsorship(minimumMoneyS);

		Money minimumMoneyI = new Money();
		minimumMoneyI.setAmount(minimumInvoice);
		minimumMoneyI.setCurrency("EUR");
		sponsorDashboard.setMinimumInvoice(minimumMoneyI);

		Money deviationMoneyS = new Money();
		deviationMoneyS.setAmount(deviationSponsorship);
		deviationMoneyS.setCurrency("EUR");
		sponsorDashboard.setDeviationSponsorship(deviationMoneyS);

		Money deviationMoneyI = new Money();
		deviationMoneyI.setAmount(deviationInvoice);
		deviationMoneyI.setCurrency("EUR");
		sponsorDashboard.setDeviationInvoice(deviationMoneyI);

		super.getBuffer().addData(sponsorDashboard);
	}

	@Override
	public void unbind(final SponsorDashboard object) {

		Dataset dataset;

		dataset = super.unbind(object, "taxedInvoices", "linkedSponsorships", "averageSponsorship", "averageInvoice", "maximumInvoice", "maximumSponsorship", "minimumInvoice", "minimumSponsorship", "deviationInvoice", "deviationSponsorship");

		super.getResponse().addData(dataset);
	}
}

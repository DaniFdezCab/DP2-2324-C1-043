
package acme.features.sponsor.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.components.MoneyService;
import acme.components.SystemCurrencyRepository;
import acme.forms.SponsorDashboard;
import acme.roles.Sponsor;

@Service
public class SponsorDashboardShowService extends AbstractService<Sponsor, SponsorDashboard> {

	@Autowired
	private SponsorDashboardRepository	repo;

	@Autowired
	private MoneyService				moneyService;

	@Autowired
	private SystemCurrencyRepository	systemRepository;


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

		taxedInvoices = this.repo.countInvoicesWithTaxLessThanOrEqualTo21(sponsorId);

		linkedSponsorships = this.repo.countLinkedSponsorships(sponsorId);

		averageSponsorship = this.repo.averageSponsorshipAmount(sponsorId);

		averageInvoice = this.repo.averageInvoiceAmount(sponsorId);

		maximumInvoice = this.repo.maxInvoiceAmount(sponsorId);

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
		averageMoney.setAmount(maximumInvoice);
		averageMoney.setCurrency("EUR");
		sponsorDashboard.setMaximumInvoice(maximumMoneyI);

		super.getBuffer().addData(sponsorDashboard);
	}

	@Override
	public void unbind(final SponsorDashboard object) {

		Dataset dataset;

		dataset = super.unbind(object, "taxedInvoices", "linkedSponsorships", "averageSponsorship", "averageInvoice");

		super.getResponse().addData(dataset);
	}
}


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

		Double deviationInvoice;

		Double deviationSponsorship;

		Double minimumInvoice;

		Double minimumSponsorship;

		Double maximumInvoice;

		Double maximumSponsorship;

		taxedInvoices = this.repo.countInvoicesWithTaxLessThanOrEqualTo21(sponsorId);
		linkedSponsorships = this.repo.countLinkedSponsorships(sponsorId);
		averageSponsorship = this.repo.averageSponsorshipAmount(sponsorId);
		averageInvoice = this.repo.averageInvoiceAmount(sponsorId);
		deviationInvoice = this.repo.deviationInvoiceAmount(sponsorId);
		deviationSponsorship = this.repo.deviationSponsorshipAmount(sponsorId);
		minimumInvoice = this.repo.minInvoiceAmount(sponsorId);
		minimumSponsorship = this.repo.minSponsorshipAmount(sponsorId);
		maximumInvoice = this.repo.maxInvoiceAmount(sponsorId);
		maximumSponsorship = this.repo.maxSponsorshipAmount(sponsorId);

		sponsorDashboard = new SponsorDashboard();

		String systemCurrency = this.systemRepository.getSystemConfiguration().get(0).getSystemCurrency();

		sponsorDashboard.setTaxedInvoices(taxedInvoices);

		sponsorDashboard.setLinkedSponsorships(linkedSponsorships);

		Money averageMoney = new Money();
		averageMoney.setAmount(averageSponsorship);
		averageMoney.setCurrency("EUR");
		sponsorDashboard.setAverageSponsorship(this.moneyService.computeMoneyExchange(averageMoney, systemCurrency).getTarget());

		Money averageMoneyI = new Money();
		averageMoneyI.setAmount(averageInvoice);
		averageMoneyI.setCurrency("EUR");
		sponsorDashboard.setAverageInvoice(this.moneyService.computeMoneyExchange(averageMoneyI, systemCurrency).getTarget());

		Money deviationMoneyS = new Money();
		averageMoney.setAmount(deviationSponsorship);
		averageMoney.setCurrency("EUR");
		sponsorDashboard.setDeviationSponsorship(this.moneyService.computeMoneyExchange(deviationMoneyS, systemCurrency).getTarget());

		Money deviationMoneyI = new Money();
		averageMoney.setAmount(deviationInvoice);
		averageMoney.setCurrency("EUR");
		sponsorDashboard.setDeviationInvoice(this.moneyService.computeMoneyExchange(deviationMoneyI, systemCurrency).getTarget());

		Money minimumMoneyS = new Money();
		averageMoney.setAmount(minimumSponsorship);
		averageMoney.setCurrency("EUR");
		sponsorDashboard.setMinimumSponsorship(this.moneyService.computeMoneyExchange(minimumMoneyS, systemCurrency).getTarget());

		Money minimumMoneyI = new Money();
		averageMoney.setAmount(minimumInvoice);
		averageMoney.setCurrency("EUR");
		sponsorDashboard.setMinimumInvoice(this.moneyService.computeMoneyExchange(minimumMoneyI, systemCurrency).getTarget());

		Money maximumMoneyS = new Money();
		averageMoney.setAmount(maximumSponsorship);
		averageMoney.setCurrency("EUR");
		sponsorDashboard.setMaximumSponsorship(this.moneyService.computeMoneyExchange(maximumMoneyS, systemCurrency).getTarget());

		Money maximumMoneyI = new Money();
		averageMoney.setAmount(maximumInvoice);
		averageMoney.setCurrency("EUR");
		sponsorDashboard.setMaximumInvoice(this.moneyService.computeMoneyExchange(maximumMoneyI, systemCurrency).getTarget());

		super.getBuffer().addData(sponsorDashboard);
	}

	@Override
	public void unbind(final SponsorDashboard object) {

		Dataset dataset;

		dataset = super.unbind(object, "taxedInvoices", "linkedSponsorships", "averageSponsorship", "averageInvoice", "deviationInvoice", "deviationSponsorship", "minimumInvoice", "minimumSponsorship", "maximumInvoice", "maximumSponsorship");

		super.getResponse().addData(dataset);
	}
}

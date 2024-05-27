
package acme.features.sponsor.dashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

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

		int sponsorId;

		SponsorDashboard dashboard;

		int taxedInvoices;

		int linkedSponsorships;

		Money averageSponsorship;

		Money deviationSponsorship;

		Money minimumSponsorship;

		Money maximumSponsorship;

		Money averageInvoice;

		Money deviationInvoice;

		Money minimumInvoice;

		Money maximumInvoice;

		sponsorId = super.getRequest().getPrincipal().getActiveRoleId();

		Collection<Money> amounts = this.repo.sponsorshipAmounts(sponsorId).stream().map(this::currencyTransformerUsd).collect(Collectors.toCollection(ArrayList<Money>::new));

		Collection<Money> quantities = this.repo.invoiceQuantities(sponsorId).stream().map(this::currencyTransformerUsd).collect(Collectors.toCollection(ArrayList<Money>::new));

		taxedInvoices = this.repo.countInvoicesWithTaxLessThanOrEqualTo21(sponsorId);

		linkedSponsorships = this.repo.countLinkedSponsorships(sponsorId);

		averageSponsorship = this.sponsorshipsAverageAmount(amounts);

		deviationSponsorship = this.sponsorshipDeviationAmount(amounts);

		minimumSponsorship = this.sponsorshipsMinimumAmount(amounts);

		maximumSponsorship = this.sponsorshipsMaximumAmount(amounts);

		averageInvoice = this.invoicesAverageQuantity(quantities);

		deviationInvoice = this.invoicesDeviationQuantity(quantities);

		minimumInvoice = this.invoicesMinimumQuantity(quantities);

		maximumInvoice = this.invoicesMaximumQuantity(quantities);

		dashboard = new SponsorDashboard();

		dashboard.setTaxedInvoices(taxedInvoices);

		dashboard.setLinkedSponsorships(linkedSponsorships);

		dashboard.setAverageSponsorship(averageSponsorship);

		dashboard.setDeviationSponsorship(deviationSponsorship);

		dashboard.setMinimumSponsorship(minimumSponsorship);

		dashboard.setMaximumSponsorship(maximumSponsorship);

		dashboard.setAverageInvoice(averageInvoice);

		dashboard.setDeviationInvoice(deviationInvoice);

		dashboard.setMinimumInvoice(minimumInvoice);

		dashboard.setMaximumInvoice(maximumInvoice);

		super.getBuffer().addData(dashboard);
	}

	private Money sponsorshipsAverageAmount(final Collection<Money> amounts) {

		Money money = new Money();

		money.setCurrency("USD");

		money.setAmount(amounts.stream().map(Money::getAmount).mapToDouble(Double::doubleValue).average().orElse(0.0));

		return money;
	}

	private Money sponsorshipsMaximumAmount(final Collection<Money> amounts) {

		Money money = new Money();

		money.setCurrency("USD");

		money.setAmount(amounts.stream().map(Money::getAmount).mapToDouble(Double::doubleValue).max().orElse(0.0));

		return money;
	}

	private Money sponsorshipsMinimumAmount(final Collection<Money> amounts) {

		Money money = new Money();

		money.setCurrency("USD");

		money.setAmount(amounts.stream().map(Money::getAmount).mapToDouble(Double::doubleValue).min().orElse(0.0));

		return money;
	}

	private Money sponsorshipDeviationAmount(final Collection<Money> amounts) {

		Money money = new Money();

		money.setCurrency("USD");

		double average = amounts.stream().mapToDouble(Money::getAmount).average().orElse(0.0);

		double sumSquares = amounts.stream().mapToDouble(x -> Math.pow(x.getAmount() - average, 2)).sum();

		double var = sumSquares / amounts.size();

		double dev = Math.sqrt(var);

		money.setAmount(dev);

		return money;
	}

	private Money invoicesAverageQuantity(final Collection<Money> quantities) {
		Money money = new Money();

		money.setCurrency("USD");

		money.setAmount(quantities.stream().map(Money::getAmount).mapToDouble(Double::doubleValue).average().orElse(0.0));

		return money;
	}

	private Money invoicesMaximumQuantity(final Collection<Money> quantities) {

		Money money = new Money();

		money.setCurrency("USD");

		money.setAmount(quantities.stream().map(Money::getAmount).mapToDouble(Double::doubleValue).max().orElse(0.0));

		return money;
	}

	private Money invoicesMinimumQuantity(final Collection<Money> quantities) {
		Money money = new Money();
		money.setCurrency("USD");
		money.setAmount(quantities.stream().map(Money::getAmount).mapToDouble(Double::doubleValue).min().orElse(0.0));
		return money;
	}

	private Money invoicesDeviationQuantity(final Collection<Money> quantities) {

		Money money = new Money();
		money.setCurrency("USD");

		double average = quantities.stream().mapToDouble(Money::getAmount).average().orElse(0.0);

		double sumSquares = quantities.stream().mapToDouble(x -> Math.pow(x.getAmount() - average, 2)).sum();

		double var = sumSquares / quantities.size();

		double dev = Math.sqrt(var);

		money.setAmount(dev);

		return money;
	}

	private Money currencyTransformerUsd(final Money initial) {

		Money res = new Money();

		res.setCurrency("USD");

		if (initial.getCurrency().equals("USD"))
			res.setAmount(initial.getAmount());

		else if (initial.getCurrency().equals("EUR"))
			res.setAmount(initial.getAmount() * 1.07);

		else
			res.setAmount(initial.getAmount() * 1.25);

		return res;
	}

	@Override
	public void unbind(final SponsorDashboard object) {

		Dataset dataset;

		dataset = super.unbind(object, "taxedInvoices", "linkedSponsorships", "averageSponsorship", "averageInvoice", "maximumInvoice", "maximumSponsorship", "minimumInvoice", "minimumSponsorship", "deviationInvoice", "deviationSponsorship");

		super.getResponse().addData(dataset);
	}
}

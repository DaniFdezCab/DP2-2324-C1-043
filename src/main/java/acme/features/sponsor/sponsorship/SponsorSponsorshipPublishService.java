
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.features.sponsor.invoice.SponsorInvoiceRepository;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipPublishService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository	repo;

	@Autowired
	private SponsorInvoiceRepository		Irepo;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int sponsorshipId;
		Sponsorship sponsorship;
		Sponsor sponsor;

		sponsorshipId = super.getRequest().getData("id", int.class);
		sponsorship = this.repo.findOneSponsorshipById(sponsorshipId);
		sponsor = sponsorship == null ? null : sponsorship.getSponsor();
		status = sponsorship != null && sponsorship.isDraftMode() && super.getRequest().getPrincipal().hasRole(sponsor);

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Sponsorship object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repo.findOneSponsorshipById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;

		super.bind(object, "code", "startMoment", "endMoment", "amount", "type", "emailContact", "moreInfo");

	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;
		int sponsorshipId;
		sponsorshipId = super.getRequest().getData("id", int.class);

		Collection<Invoice> li = this.Irepo.findInvoicesBySponsorshipId(sponsorshipId);
		Sponsorship s = this.repo.findOneSponsorshipById(sponsorshipId);
		double amount = s.getAmount().getAmount();
		double cant = 0.0;
		for (Invoice i : li)
			cant += i.getQuantity().getAmount();
		super.state(amount == cant, "*", "sponsor.sponsorship.form.error.invoice.error");

	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;

		object.setDraftMode(false);

		this.repo.save(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "startMoment", "endMoment", "amount", "type", "emailContact", "moreInfo", "draftMode");

		super.getResponse().addData(dataset);

	}

}

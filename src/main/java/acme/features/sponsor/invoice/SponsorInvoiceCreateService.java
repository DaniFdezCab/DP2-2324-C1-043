
package acme.features.sponsor.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceCreateService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	private SponsorInvoiceRepository repo;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Invoice object;
		int sponsorshipId;
		Sponsorship sponsorship;

		sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		sponsorship = this.repo.findOneSponsorshipById(sponsorshipId);

		object = new Invoice();
		object.setSponsorship(sponsorship);
		object.setDraftMode(true);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;

		super.bind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "moreInfo");

	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {

			Invoice existing = this.repo.findOneInvoiceByCode(object.getCode());

			if (existing != null)
				super.state(existing.getId() == object.getId(), "code", "sponsor.invoice.form.error.code");
		}

		if (!super.getBuffer().getErrors().hasErrors("quantity"))
			super.state(object.getQuantity().getAmount() >= 0, "quantity", "sponsor.invoice.form.error.quantity");
	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;

		this.repo.save(object);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "moreInfo", "draftMode");
		dataset.put("sponsorshipId", super.getRequest().getData("sponsorshipId", int.class));

		super.getResponse().addData(dataset);
	}

}

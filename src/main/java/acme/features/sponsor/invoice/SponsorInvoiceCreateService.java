
package acme.features.sponsor.invoice;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
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
		boolean status;
		int sponsorshipId;
		int sponsorId;
		Sponsorship sponsorship;

		sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		sponsorship = this.repo.findOneSponsorshipById(sponsorshipId);

		sponsorId = super.getRequest().getPrincipal().getActiveRoleId();

		status = sponsorId == sponsorship.getSponsor().getId();

		super.getResponse().setAuthorised(status);
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

		if (!super.getBuffer().getErrors().hasErrors("dueDate")) {
			Date registrationTime;
			Date dueDate;

			registrationTime = object.getRegistrationTime();
			dueDate = object.getDueDate();

			super.state(MomentHelper.isLongEnough(registrationTime, dueDate, 1, ChronoUnit.MONTHS) && dueDate.after(registrationTime), "dueDate", "sponsor.invoice.form.error.dueDate");
		}

		if (!super.getBuffer().getErrors().hasErrors("quantity"))
			super.state(object.getQuantity().getAmount() >= 0, "amount", "sponsor.invoice.form.error.negative-amount");

		if (!super.getBuffer().getErrors().hasErrors("quantity"))
			super.state(object.getQuantity().getCurrency().equals("GBP") || object.getQuantity().getCurrency().equals("EUR") || object.getQuantity().getCurrency().equals("USD"), "amount", "sponsor.invoice.form.error.acceptedCurrency");
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

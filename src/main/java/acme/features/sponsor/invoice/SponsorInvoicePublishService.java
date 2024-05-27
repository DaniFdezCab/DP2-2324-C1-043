
package acme.features.sponsor.invoice;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Invoice;
import acme.roles.Sponsor;

@Service
public class SponsorInvoicePublishService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	SponsorInvoiceRepository repo;


	@Override
	public void authorise() {
		boolean status;
		int invoiceId;
		Invoice invoice;

		invoiceId = super.getRequest().getData("id", int.class);
		invoice = this.repo.findOneInvoiceById(invoiceId);
		status = invoice.isDraftMode() && invoice != null && super.getRequest().getPrincipal().hasRole(invoice.getSponsorship().getSponsor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Invoice object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repo.findOneInvoiceById(id);

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
			Invoice existing;

			existing = this.repo.findOneInvoiceByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "sponsor.invoice.form.error.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("dueDate")) {
			Date registrationTime;
			Date dueDate;

			registrationTime = object.getRegistrationTime();
			dueDate = object.getDueDate();

			super.state(MomentHelper.isLongEnough(registrationTime, dueDate, 1, ChronoUnit.MONTHS) && dueDate.after(registrationTime), "dueDate", "sponsor.invoice.form.error.dueDate");
		}

		if (!super.getBuffer().getErrors().hasErrors("quantity"))
			super.state(object.getQuantity().getAmount() >= 0, "quantity", "sponsor.invoice.form.error.negative-amount");

		if (!super.getBuffer().getErrors().hasErrors("quantity"))
			super.state(object.getQuantity().getCurrency().equals("GBP") || object.getQuantity().getCurrency().equals("EUR") || object.getQuantity().getCurrency().equals("USD"), "quantity", "sponsor.invoice.form.error.acceptedCurrency");

	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;

		object.setDraftMode(false);

		this.repo.save(object);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;
		Dataset dataset;

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "moreInfo", "draftMode");

		super.getResponse().addData(dataset);

	}
}

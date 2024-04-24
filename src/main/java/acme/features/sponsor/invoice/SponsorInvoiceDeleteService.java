
package acme.features.sponsor.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Invoice;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceDeleteService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	private SponsorInvoiceRepository repo;


	@Override
	public void authorise() {

		boolean status;
		int id;
		Invoice i;
		Sponsor sponsor;

		id = super.getRequest().getData("id", int.class);
		i = this.repo.findOneInvoiceById(id);
		sponsor = i == null ? null : i.getSponsorship().getSponsor();
		status = i != null && i.isDraftMode() && super.getRequest().getPrincipal().hasRole(sponsor);

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
	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;

		this.repo.delete(object);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "moreInfo", "draftMode");
		dataset.put("sponsorshipId", super.getRequest().getData("sponsorshipId", int.class));
		dataset.put("draftMode", object.getSponsorship().isDraftMode());

		super.getResponse().addData(dataset);
	}
}

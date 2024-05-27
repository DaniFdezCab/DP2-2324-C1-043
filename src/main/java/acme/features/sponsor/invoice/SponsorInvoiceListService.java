
package acme.features.sponsor.invoice;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceListService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	private SponsorInvoiceRepository repo;


	@Override
	public void authorise() {

		boolean status;

		int id;
		Sponsorship spo;
		Sponsor sponsor;

		id = super.getRequest().getData("sponsorshipId", int.class);
		spo = this.repo.findOneSponsorshipById(id);
		sponsor = spo == null ? null : spo.getSponsor();

		status = spo != null && super.getRequest().getPrincipal().hasRole(sponsor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Invoice> objects;
		int sponsorshipId;

		sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		objects = this.repo.findInvoicesBySponsorshipId(sponsorshipId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "moreInfo", "draftMode");
		dataset.put("totalAmount", object.totalAmount());

		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<Invoice> object) {
		assert object != null;

		int sponsorshipId;

		sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);

		super.getResponse().addGlobal("sponsorshipId", sponsorshipId);

	}

}

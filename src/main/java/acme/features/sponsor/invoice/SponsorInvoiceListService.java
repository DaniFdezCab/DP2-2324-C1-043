
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
		int sponsorshipId;
		Sponsorship sponsorship;
		Sponsor sponsor;

		sponsorshipId = super.getRequest().getData("id", int.class);
		sponsorship = this.repo.findOneSponsorshipById(sponsorshipId);
		sponsor = sponsorship == null ? null : sponsorship.getSponsor();
		status = sponsorship != null && !sponsorship.getPublished() && super.getRequest().getPrincipal().hasRole(sponsor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Invoice> object;
		int sponsorId;

		sponsorId = super.getRequest().getData("sponsorId", int.class);
		object = this.repo.findInvoicesBySponsorshipId(sponsorId);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "moreInfo", "published");

		super.getResponse().addData(dataset);
	}
}

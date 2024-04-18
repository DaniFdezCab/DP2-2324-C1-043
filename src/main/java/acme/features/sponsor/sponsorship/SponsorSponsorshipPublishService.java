
package acme.features.sponsor.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipPublishService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository repo;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int sponsorId;
		Sponsorship sponsorship;
		Sponsor sponsor;

		sponsorId = super.getRequest().getData("sponsorId", int.class);
		sponsorship = this.repo.findOneSponsorshipById(sponsorId);
		sponsor = sponsorship == null ? null : sponsorship.getSponsor();
		status = sponsorship != null && (!sponsorship.getPublished() || super.getRequest().getPrincipal().hasRole(sponsor));

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
	public void unbind(final Sponsorship object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "duration", "amount", "type", "emailContact", "moreInfo", "notPublished");

		super.getResponse().addData(dataset);
	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;

		this.repo.save(object);
	}

}

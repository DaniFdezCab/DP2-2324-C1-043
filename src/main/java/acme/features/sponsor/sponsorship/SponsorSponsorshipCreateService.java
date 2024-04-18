
package acme.features.sponsor.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipCreateService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	private SponsorSponsorshipRepository repo;


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
		Sponsor sponsor;

		sponsor = this.repo.findOneSponsorById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Sponsorship();
		object.setSponsor(sponsor);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;

		int sponsorId;
		Sponsor sponsor;

		sponsorId = super.getRequest().getData("sponsor", int.class);
		sponsor = this.repo.findOneSponsorById(sponsorId);

		super.bind(object, "code", "duration", "amount", "type", "emailContact", "moreInfo", "Published");
		object.setSponsor(sponsor);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "duration", "amount", "type", "emailContact", "moreInfo", "Published");

		super.getResponse().addData(dataset);
	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;

		this.repo.save(object);
	}

}

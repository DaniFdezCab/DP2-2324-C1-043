
package acme.features.sponsor.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipUpdateService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	private SponsorSponsorshipRepository repo;


	@Override
	public void authorise() {
		boolean status;
		int sponsorId;
		Sponsorship sponsorship;

		sponsorId = super.getRequest().getData("id", int.class);
		sponsorship = this.repo.findOneSponsorshipById(sponsorId);
		status = sponsorship != null && (!sponsorship.getPublished() || super.getRequest().getPrincipal().hasRole(Sponsor.class));

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Sponsorship object;
		int sponsorId;

		sponsorId = super.getRequest().getData("id", int.class);
		object = this.repo.findOneSponsorshipById(sponsorId);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;

		super.bind(object, "code", "duration", "amount", "type", "emailContact", "moreInfo");

	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;
	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;

		this.repo.save(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "duration", "amount", "type", "emailContact", "moreInfo");
		dataset.put("sponsorId", object.getSponsor().getId());
	}

}

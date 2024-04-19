
package acme.features.sponsor.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipDeleteService extends AbstractService<Sponsor, Sponsorship> {

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
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repo.findOneSponsorshipById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "moment", "duration", "amount", "type", "emailContact", "moreInfo", "published");

		super.getResponse().addData(dataset);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repo.findOneProjectById(projectId);

		super.bind(object, "code", "moment", "duration", "amount", "type", "emailContact", "moreInfo");
		object.setProject(project);

	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;
	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;

		this.repo.delete(object);
	}

}

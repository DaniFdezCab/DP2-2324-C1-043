
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipDeleteService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	private SponsorSponsorshipRepository repo;


	@Override
	public void authorise() {
		boolean status;
		int sponsorshipId;
		Sponsorship sponsorship;
		Sponsor sponsor;

		sponsorshipId = super.getRequest().getData("id", int.class);
		sponsorship = this.repo.findOneSponsorshipById(sponsorshipId);
		sponsor = sponsorship == null ? null : sponsorship.getSponsor();
		status = sponsorship != null && sponsorship.isDraftMode() && super.getRequest().getPrincipal().hasRole(sponsor);

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
	public void bind(final Sponsorship object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repo.findOneProjectById(projectId);

		super.bind(object, "code", "startMoment", "endMoment", "amount", "type", "emailContact", "moreInfo");
		object.setProject(project);

	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;
	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;

		Collection<Invoice> us;
		us = this.repo.findInvoicesBySponsorshipId(object.getId());
		this.repo.deleteAll(us);

		this.repo.delete(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;

		projects = this.repo.findAllProjects();
		choices = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "code", "startMoment", "endMoment", "amount", "type", "emailContact", "moreInfo", "draftMode");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}

}


package acme.features.sponsor.sponsorship;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.sponsorships.SponsorshipType;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipCreateService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state -----------------------------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository repository;

	//AbstractService interface -------------------------------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Sponsorship object;
		Sponsor sponsor;

		sponsor = this.repository.findOneSponsorById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Sponsorship();
		object.setDraftMode(true);
		object.setSponsor(sponsor);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;

		super.bind(object, "code", "moment", "startDuration", "endDuration", "amount", "type", "emailContact", "moreInfo", "project");
	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Sponsorship existing;

			existing = this.repository.findOneSponsorshipByCode(object.getCode());
			super.state(existing == null, "code", "sponsor.sponsorship.form.error.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("startDuration")) {
			Date startDuration;
			Date moment;
			startDuration = object.getStartDuration();
			moment = object.getMoment();

			super.state(startDuration.after(moment), "startDuration", "sponsor.sponsorship.form.error.startDuration");
		}

		if (!super.getBuffer().getErrors().hasErrors("endDuration")) {
			Date endDuration;
			Date startDuration;

			startDuration = object.getStartDuration();
			endDuration = object.getEndDuration();

			super.state(MomentHelper.isLongEnough(startDuration, endDuration, 1, ChronoUnit.MONTHS) && endDuration.after(startDuration), "endDuration", "sponsor.sponsorship.form.error.endDuration");
		}

		if (!super.getBuffer().getErrors().hasErrors("amount"))
			super.state(object.getAmount().getAmount() >= 0, "amount", "sponsor.sponsorship.form.error.negative-amount");

	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;
		SelectChoices choices2;

		choices2 = SelectChoices.from(SponsorshipType.class, object.getType());
		projects = this.repository.findAllProjects();
		choices = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "code", "moment", "startDuration", "endDuration", "amount", "type", "emailContact", "moreInfo", "project", "draftMode");
		dataset.put("types", choices2);
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}

}

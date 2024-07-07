
package acme.features.developer.trainingSession;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.entities.trainings.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionPublishService extends AbstractService<Developer, TrainingSession> {

	@Autowired
	private DeveloperTrainingSessionRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int id;
		TrainingSession session;

		id = super.getRequest().getData("id", int.class);
		session = this.repository.findOneTrainingSessionById(id);

		status = session != null && session.getNotPublished() && super.getRequest().getPrincipal().hasRole(session.getTrainingModule().getDeveloper());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingSession object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTrainingSessionById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;

		super.bind(object, "code", "location", "instructor", "startMoment", "endMoment", "email", "link");
		object.setNotPublished(object.getNotPublished());
	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingSession existing;

			existing = this.repository.findOneTrainingSessionByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "developer.training-session.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("startMoment")) {
			Date minimum;

			minimum = MomentHelper.deltaFromMoment(object.getTrainingModule().getCreationMoment(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(object.getStartMoment(), minimum), "startMoment", "developer.training-session.form.error.too-early");
		}

		if (!super.getBuffer().getErrors().hasErrors("startMoment"))
			super.state(MomentHelper.isAfter(object.getEndMoment(), object.getStartMoment()), "startMoment", "developer.training-session.form.error.invalid-period");

		if (!super.getBuffer().getErrors().hasErrors("endMoment")) {
			Date startMoment;

			startMoment = MomentHelper.deltaFromMoment(object.getStartMoment(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(object.getEndMoment(), startMoment), "endMoment", "developer.training-session.form.error.too-short-period");
		}
	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		object.setNotPublished(!object.getNotPublished());
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "location", "instructor", "startMoment", "endMoment", "email", "link");
		dataset.put("masterId", object.getTrainingModule().getId());
		dataset.put("notPublished", object.getNotPublished());
		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}

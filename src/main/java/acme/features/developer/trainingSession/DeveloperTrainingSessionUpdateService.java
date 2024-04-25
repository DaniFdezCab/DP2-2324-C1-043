
package acme.features.developer.trainingSession;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.trainings.TrainingModule;
import acme.entities.trainings.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionUpdateService extends AbstractService<Developer, TrainingSession> {

	@Autowired
	private DeveloperTrainingSessionRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int sessionId;
		TrainingModule module;
		TrainingSession session;

		sessionId = super.getRequest().getData("id", int.class);
		module = this.repository.findOneTrainingModuleByTrainingSessionId(sessionId);
		session = this.repository.findOneTrainingSessionById(sessionId);

		status = module != null && session.getNotPublished() && super.getRequest().getPrincipal().hasRole(module.getDeveloper());

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
	}

	@Override
	public void validate(final TrainingSession object) {
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

		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "location", "instructor", "startMoment", "endMoment", "email", "link", "notPublished");
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));
		//dataset.put("notPublishedModule", object.getTrainingModule().getNotPublished());

		super.getResponse().addData(dataset);
	}

}

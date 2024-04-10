
package acme.features.developer.trainingModule;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.trainings.DifficultyLevel;
import acme.entities.trainings.TrainingModule;
import acme.entities.trainings.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleDeleteService extends AbstractService<Developer, TrainingModule> {

	@Autowired
	protected DeveloperTrainingModuleRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		TrainingModule module;
		Developer developer;

		masterId = super.getRequest().getData("id", int.class);
		module = this.repository.findOneTrainingModuleById(masterId);
		developer = module == null ? null : module.getDeveloper();
		status = module != null && module.getNotPublished() && super.getRequest().getPrincipal().hasRole(developer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingModule object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTrainingModuleById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);
		object.setProject(project);

		super.bind(object, "code", "details", "link", "totalTime");
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;
	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;

		Collection<TrainingSession> sessions;

		sessions = this.repository.findManyTrainingSessionsByTrainingModuleId(object.getId());
		this.repository.deleteAll(sessions);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;

		projects = this.repository.findAllProjects();
		choices = SelectChoices.from(projects, "title", object.getProject());

		SelectChoices difficulty;
		difficulty = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());

		dataset = super.unbind(object, "code", "details", "creationMoment", "link", "totalTime", "notPublished");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);
		dataset.put("difficultyLevel", difficulty);

		super.getResponse().addData(dataset);
	}
}

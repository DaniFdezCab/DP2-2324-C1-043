
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
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleShowService extends AbstractService<Developer, TrainingModule> {

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
		status = super.getRequest().getPrincipal().hasRole(developer) || module != null;

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
		dataset.put("projectId", object.getProject().getTitle());
		dataset.put("projects", choices);
		dataset.put("difficultyLevel", difficulty);

		super.getResponse().addData(dataset);
	}

}

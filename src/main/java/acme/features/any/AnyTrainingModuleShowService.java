
package acme.features.any;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.trainings.TrainingModule;

@Service
public class AnyTrainingModuleShowService extends AbstractService<Any, TrainingModule> {

	@Autowired
	private AnyTrainingModuleRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int id;
		TrainingModule module;

		id = super.getRequest().getData("id", int.class);
		module = this.repository.findOneTrainingModuleById(id);
		status = module != null && !module.getNotPublished();

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

		dataset = super.unbind(object, "code", "details", "creationMoment", "link", "totalTime", "notPublished");
		dataset.put("projectId", object.getProject().getTitle());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}

}

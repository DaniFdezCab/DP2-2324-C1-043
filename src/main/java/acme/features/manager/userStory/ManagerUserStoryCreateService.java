
package acme.features.manager.userStory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.AssociationProject;
import acme.entities.projects.Priority;
import acme.entities.projects.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryCreateService extends AbstractService<Manager, UserStory> {

	@Autowired
	private ManagerUserStoryRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		UserStory object;
		Manager manager;

		manager = this.repository.findOneManagerById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new UserStory();
		object.setPublished(false);
		object.setManager(manager);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final UserStory object) {
		assert object != null;

		super.bind(object, "title", "description", "estimatedCost", "priority", "acceptanceCriteria", "url");
	}

	@Override
	public void validate(final UserStory object) {
		assert object != null;

	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;

		this.repository.save(object);

		if (super.getRequest().hasData("projectId", int.class)) {
			final AssociationProject ap = new AssociationProject();
			ap.setProject(this.repository.findOneProjectById(super.getRequest().getGlobal("projectId", int.class)));
			ap.setUserStory(object);
			this.repository.save(ap);
		}

	}

	//	@Override
	//	public void perform(final UserStory object) {
	//		assert object != null;
	//		int projectId;
	//		AssociationProject ap = new AssociationProject();
	//		Project project;
	//
	//		projectId = super.getRequest().getData("projectId", int.class);
	//		project = this.repository.findOneProjectById(projectId);
	//
	//		ap.setProject(project);
	//		ap.setUserStory(object);
	//
	//		this.repository.save(object);
	//		this.repository.save(ap);
	//	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		SelectChoices choices;

		choices = SelectChoices.from(Priority.class, object.getPriority());

		Dataset dataset;

		dataset = super.unbind(object, "title", "description", "estimatedCost", "priority", "acceptanceCriteria", "url", "published");

		dataset.put("priority", choices);
		super.getResponse().addData(dataset);
	}

}

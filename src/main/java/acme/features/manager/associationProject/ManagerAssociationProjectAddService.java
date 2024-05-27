
package acme.features.manager.associationProject;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.AssociationProject;
import acme.entities.projects.Project;
import acme.entities.projects.UserStory;
import acme.roles.Manager;

@Service
public class ManagerAssociationProjectAddService extends AbstractService<Manager, AssociationProject> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerAssociationProjectRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int projectId;
		Project project;

		projectId = super.getRequest().getData("projectId", int.class);
		project = this.repository.findOneProjectById(projectId);
		status = project != null && super.getRequest().getPrincipal().hasRole(project.getManager());

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		AssociationProject object;
		Project project;

		project = this.repository.findOneProjectById(super.getRequest().getData("projectId", int.class));
		object = new AssociationProject();
		object.setProject(project);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final AssociationProject object) {
		assert object != null;

		super.bind(object, "userStory");
	}

	@Override
	public void validate(final AssociationProject object) {
		assert object != null;

	}

	@Override
	public void perform(final AssociationProject object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final AssociationProject object) {
		assert object != null;

		int managerId;
		Collection<UserStory> storiesManager;
		Collection<UserStory> storiesProject;

		SelectChoices choices;
		Dataset dataset;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();
		storiesManager = this.repository.findManyUserStoriesByManagerId(managerId);
		storiesProject = this.repository.findManyUserStoriesByProjectId(object.getProject().getId());
		storiesManager.removeAll(storiesProject);
		choices = SelectChoices.from(storiesManager, "title", object.getUserStory());

		dataset = super.unbind(object, "project");
		dataset.put("userStory", choices.getSelected().getKey());
		dataset.put("userStories", choices);
		dataset.put("projectId", object.getProject().getId());

		super.getResponse().addData(dataset);
	}

}

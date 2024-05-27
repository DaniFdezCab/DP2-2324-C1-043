
package acme.features.manager.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.projects.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectPublishService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository repository;

	// AbstractService<Manager, Project> -------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int projectId;
		Project project;
		Manager manager;

		projectId = super.getRequest().getData("id", int.class);
		project = this.repository.findOneProjectById(projectId);
		manager = project == null ? null : project.getManager();
		status = project != null && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;

		super.bind(object, "code", "title", "summary", "cost", "url", "fatalErrors");

	}

	@Override
	public void validate(final Project object) {
		assert object != null;
		List<UserStory> publishedUS = (List<UserStory>) this.repository.findManyPublishedUserStoriesByProjectId(object.getId());
		List<UserStory> projectUS = (List<UserStory>) this.repository.findManyUserStoriesByProjectId(object.getId());

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Project existing;

			existing = this.repository.findOneProjectByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "manager.project.form.error.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("fatalErrors"))
			super.state(object.isFatalErrors() == false, "fatalErrors", "manager.project.form.error.fatalErrors");

		if (!super.getBuffer().getErrors().hasErrors("published"))
			super.state(!projectUS.isEmpty() && projectUS.size() == publishedUS.size(), "published", "manager.project.form.error.publish");

		if (!super.getBuffer().getErrors().hasErrors("cost"))
			super.state(object.getCost().getAmount() > 0, "cost", "manager.project.form.error.negative-cost");
	}

	@Override
	public void perform(final Project object) {
		assert object != null;
		object.setPublished(!object.isPublished());

		this.repository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "summary", "cost", "url", "fatalErrors", "published");

		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}

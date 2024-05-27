
package acme.features.manager.userStory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
<<<<<<< HEAD
import acme.entities.projects.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryUpdateService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository repository;

	// AbstractService<Manager, Project> -------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int userStoryId;
		UserStory userStory;
		Manager manager;

		userStoryId = super.getRequest().getData("id", int.class);
		userStory = this.repository.findOneUserStoryById(userStoryId);
		manager = userStory == null ? null : userStory.getManager();
		status = userStory != null && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		UserStory object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneUserStoryById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final UserStory object) {
		assert object != null;

		int managerId;
		Manager manager;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();
		manager = this.repository.findOneManagerById(managerId);

		super.bind(object, "title", "description", "estimatedCost", "priority", "acceptanceCriteria", "url");
		object.setManager(manager);
	}

	@Override
	public void validate(final UserStory object) {
		assert object != null;
	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;

		this.repository.save(object);
	}
	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "title", "description", "estimatedCost", "priority", "acceptanceCriteria", "url", "published");

=======
import acme.client.views.SelectChoices;
import acme.entities.projects.Priority;
import acme.entities.projects.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryUpdateService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository repository;

	// AbstractService<Manager, Project> -------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int userStoryId;
		UserStory userStory;
		Manager manager;

		userStoryId = super.getRequest().getData("id", int.class);
		userStory = this.repository.findOneUserStoryById(userStoryId);
		manager = userStory == null ? null : userStory.getManager();
		status = userStory != null && !userStory.isPublished() && this.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		UserStory object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneUserStoryById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final UserStory object) {
		assert object != null;

		int managerId;
		Manager manager;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();
		manager = this.repository.findOneManagerById(managerId);

		super.bind(object, "title", "description", "estimatedCost", "priority", "acceptanceCriteria", "url");
		object.setManager(manager);
	}

	@Override
	public void validate(final UserStory object) {
		assert object != null;
	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;

		this.repository.save(object);
	}
	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		SelectChoices choices;
		Dataset dataset;

		choices = SelectChoices.from(Priority.class, object.getPriority());

		dataset = super.unbind(object, "title", "description", "estimatedCost", "priority", "acceptanceCriteria", "url", "published");
		dataset.put("priority", choices);
>>>>>>> branch 'D04-alepingar' of https://github.com/DaniFdezCab/DP2-2324-C1-043.git
		super.getResponse().addData(dataset);
	}

}

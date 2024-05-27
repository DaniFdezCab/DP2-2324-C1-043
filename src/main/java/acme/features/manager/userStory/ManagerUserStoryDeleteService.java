
package acme.features.manager.userStory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.AssociationProject;
import acme.entities.projects.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryDeleteService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int userStoryId;
		UserStory userStory;

		userStoryId = super.getRequest().getData("id", int.class);
		userStory = this.repository.findOneUserStoryById(userStoryId);
		status = userStory != null && !userStory.isPublished() && super.getRequest().getPrincipal().hasRole(userStory.getManager());

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

		super.bind(object, "title", "description", "estimatedCost", "priority", "acceptanceCriteria", "url");

	}

	@Override
	public void validate(final UserStory object) {
		assert object != null;
	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;

		List<AssociationProject> associationProjects;
		int id = object.getId();

		associationProjects = (List<AssociationProject>) this.repository.findManyAssociationProjectByUserStoryId(id);

		this.repository.deleteAll(associationProjects);

		this.repository.delete(object);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		Dataset dataset;

<<<<<<< HEAD
		dataset = super.unbind(object, "title", "description", "estimatedCost", "priority", "acceptanceCriteria", "url");
=======
		dataset = super.unbind(object, "title", "description", "estimatedCost", "priority", "	acceptanceCriteria", "url");
>>>>>>> branch 'D04-alepingar' of https://github.com/DaniFdezCab/DP2-2324-C1-043.git

		super.getResponse().addData(dataset);
	}
}

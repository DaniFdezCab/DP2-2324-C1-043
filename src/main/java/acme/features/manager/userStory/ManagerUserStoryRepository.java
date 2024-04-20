
package acme.features.manager.userStory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.projects.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerUserStoryRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select us from UserStory us where us.id = :id")
	UserStory findOneUserStoryById(int id);

	//@Query("select us from AssociationProject ap JOIN UserStory us WHERE ap.project.id = :id AND ap.userStory.id = us.id")

	@Query("select us from UserStory us")
	Collection<UserStory> findManyUserStoriesByProjectId(int id);

	@Query("select m from Manager m where m.id = :id")
	Manager findOneManagerById(int id);
}

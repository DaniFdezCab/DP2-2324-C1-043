
package acme.features.manager.associationProject;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.projects.UserStory;

@Repository
public interface ManagerAssociationProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select us from UserStory us where us.manager.id = :id")
	Collection<UserStory> findManyUserStoriesByManagerId(int id);

	@Query("select us from AssociationProject ap join UserStory us on ap.userStory.id = us.id where ap.project.id = :id")
	Collection<UserStory> findManyUserStoriesByProjectId(int id);
}

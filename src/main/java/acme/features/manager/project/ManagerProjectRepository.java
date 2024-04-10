
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select p from Project p where p.manager.id = :id")
	Collection<Project> findManyProjectsByManagerId(int id);

	@Query("select p from Project p where p.manager.id = :id and p.published = true")
	Collection<Project> findManyPublishedProjectsByManagerId(int id);

}

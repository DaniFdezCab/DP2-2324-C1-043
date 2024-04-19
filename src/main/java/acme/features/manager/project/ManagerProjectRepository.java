
package acme.features.manager.project;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contracts.Contract;
import acme.entities.contracts.ProgressLogs;
import acme.entities.projects.Project;
import acme.entities.projects.UserStory;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Manager;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select m from Manager m where m.id = :id")
	Manager findOneManagerById(int id);

	@Query("select p from Project p where p.manager.id = :id")
	Collection<Project> findManyProjectsByManagerId(int id);

	@Query("select p from Project p where p.manager.id = :id and p.published = true")
	Collection<Project> findManyPublishedProjectsByManagerId(int id);

	@Query("select p from Project p where p.code = :code")
	Project findOneProjectByCode(String code);

	@Query("select us from UserStory us where us.project.id = :id")
	Collection<UserStory> findManyUserStoriesByProjectId(int id);

	@Query("select c from Contract c where c.project.id = :id")
	Collection<Contract> findManyContractsByProjectId(int id);

	@Query("select pl from ProgressLogs pl where pl.contract.id IN :ids")
	Collection<ProgressLogs> findManyProgressLogsByContractIds(Set<Integer> ids);

	@Query("select ss from Sponsorship ss where ss.project.id = :id")
	Collection<Sponsorship> findManySponsorshipsByProjectId(int id);

	@Query("select i from Invoice i where i.sponsorship.id IN :ids")
	Collection<Invoice> findManyInvoicesBySponsorshipIds(Set<Integer> ids);

}

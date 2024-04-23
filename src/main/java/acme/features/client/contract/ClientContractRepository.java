
package acme.features.client.contract;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contracts.Contract;
import acme.entities.contracts.ProgressLog;
import acme.entities.projects.Project;
import acme.roles.Client;

@Repository
public interface ClientContractRepository extends AbstractRepository {

	@Query("select c from Contract c where c.id = :id")
	Contract findOneContractById(int id);

	@Query("select c from Contract c where c.client.id = :id")
	List<Contract> findManyContractsByClientId(int id);

	@Query("select c from Client c where c.id = :id")
	Client findOneClientById(int id);

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select c from Contract c where c.code = :code")
	Contract findOneContractByCode(String code);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select c.project from Contract c where c.id = :id")
	Collection<Project> findOneProjectByContractId(int id);

	@Query("select pr from ProgressLog pr where pr.contract.id = :contractId")
	Collection<ProgressLog> findManyProgressLogByContractId(int contractId);

	@Query("select c from Contract c where c.project.id = :id")
	Collection<Contract> findManyContractByProjectId(int id);

}

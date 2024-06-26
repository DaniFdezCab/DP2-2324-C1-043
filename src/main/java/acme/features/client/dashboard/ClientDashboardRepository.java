
package acme.features.client.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.datatypes.Money;
import acme.client.repositories.AbstractRepository;
import acme.entities.contracts.Contract;
import acme.entities.contracts.ProgressLog;

@Repository
public interface ClientDashboardRepository extends AbstractRepository {

	@Query("select pl from ProgressLog pl")
	Collection<ProgressLog> findAllProgressLogs();

	@Query("select c from Contract c")
	Collection<Contract> findAllContracts();

	@Query("select c from Contract c where c.client.id = :clientId")
	Collection<Contract> findManyContractsByClientId(int clientId);

	@Query("select c.budget from Contract c where c.client.id = :clientId and c.draftMode = false")
	Collection<Money> findManyBudgetsByClientId(int clientId);
}

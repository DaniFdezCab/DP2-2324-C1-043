
package acme.features.manager.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select count(us) from UserStory us where us.manager.id = :id")
	int countManyUserStoriesByManagerId(int id);

	@Query("select avg(us.estimatedCost) from UserStory us where us.manager.id = :id")
	double averageCostUserStoriesByManagerId(int id);

	@Query("select stddev(us.estimatedCost) from UserStory us where us.manager.id = :id")
	double deviationCostUserStoriesByManagerId(int id);

	@Query("select min(us.estimatedCost) from UserStory us where us.manager.id = :id")
	int minimumCostUserStoriesByManager(int id);

	@Query("select max(us.estimatedCost) from UserStory us where us.manager.id = :id")
	int maximumCostUserStoriesByManager(int id);

	@Query("select avg(p.cost) from Project p where p.manager.id = :id")
	double averageCostProjectsByManagerId(int id);

	@Query("select stddev(p.cost) from Project p where p.manager.id = :id")
	double deviationCostProjectsByManagerId(int id);

	@Query("select min(p.cost) from Project p where p.manager.id = :id")
	int minimumCostProjectsByManager(int id);

	@Query("select max(p.cost) from Project p where p.manager.id = :id")
	int maximumCostProjectsByManager(int id);

}

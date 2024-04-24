
package acme.features.manager.dashboard;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select count(us) from UserStory us where us.manager.id = :id")
	Optional<Integer> countManyUserStoriesByManagerId(int id);

	@Query("select avg(us.estimatedCost) from UserStory us where us.manager.id = :id")
	Optional<Double> averageCostUserStoriesByManagerId(int id);

	@Query("select stddev(us.estimatedCost) from UserStory us where us.manager.id = :id")
	Optional<Double> deviationCostUserStoriesByManagerId(int id);

	@Query("select min(us.estimatedCost) from UserStory us where us.manager.id = :id")
	Optional<Integer> minimumCostUserStoriesByManager(int id);

	@Query("select max(us.estimatedCost) from UserStory us where us.manager.id = :id")
	Optional<Integer> maximumCostUserStoriesByManager(int id);

	@Query("select avg(p.cost.amount) from Project p where p.manager.id = :id")
	Optional<Double> averageCostProjectsByManagerId(int id);

	@Query("select stddev(p.cost.amount) from Project p where p.manager.id = :id")
	Optional<Double> deviationCostProjectsByManagerId(int id);

	@Query("select min(p.cost.amount) from Project p where p.manager.id = :id")
	Optional<Double> minimumCostProjectsByManager(int id);

	@Query("select max(p.cost.amount) from Project p where p.manager.id = :id")
	Optional<Double> maximumCostProjectsByManager(int id);

}


package acme.features.manager.dashboard;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select count(us) from UserStory us where us.manager.id = :id and us.published = true")
	Optional<Integer> countManyUserStoriesByManagerId(int id);

	@Query("select count(us) from UserStory us where (us.manager.id = :id and us.priority = acme.entities.projects.Priority.MUST and us.published = true)")
	Optional<Integer> countManyUserStoriesMUSTByManagerId(int id);

	@Query("select count(us) from UserStory us where (us.manager.id = :id and us.priority = acme.entities.projects.Priority.SHOULD and us.published = true)")
	Optional<Integer> countManyUserStoriesSHOULDByManagerId(int id);

	@Query("select count(us) from UserStory us where (us.manager.id = :id and us.priority = acme.entities.projects.Priority.COULD and us.published = true)")
	Optional<Integer> countManyUserStoriesCOULDByManagerId(int id);

	@Query("select count(us) from UserStory us where (us.manager.id = :id and us.priority = acme.entities.projects.Priority.WONT and us.published = true)")
	Optional<Integer> countManyUserStoriesWONTByManagerId(int id);

	@Query("select avg(us.estimatedCost) from UserStory us where us.manager.id = :id and us.published = true ")
	Optional<Double> averageCostUserStoriesByManagerId(int id);

	@Query("select stddev(us.estimatedCost) from UserStory us where us.manager.id = :id and us.published = true")
	Optional<Double> deviationCostUserStoriesByManagerId(int id);

	@Query("select min(us.estimatedCost) from UserStory us where us.manager.id = :id and us.published = true")
	Optional<Integer> minimumCostUserStoriesByManager(int id);

	@Query("select max(us.estimatedCost) from UserStory us where us.manager.id = :id and us.published = true")
	Optional<Integer> maximumCostUserStoriesByManager(int id);

	@Query("select avg(p.cost.amount) from Project p where p.manager.id = :id and p.published = true")
	Optional<Double> averageCostProjectsByManagerId(int id);

	@Query("select stddev(p.cost.amount) from Project p where p.manager.id = :id and p.published = true")
	Optional<Double> deviationCostProjectsByManagerId(int id);

	@Query("select min(p.cost.amount) from Project p where p.manager.id = :id and p.published = true")
	Optional<Double> minimumCostProjectsByManager(int id);

	@Query("select max(p.cost.amount) from Project p where p.manager.id = :id and p.published = true")
	Optional<Double> maximumCostProjectsByManager(int id);

}

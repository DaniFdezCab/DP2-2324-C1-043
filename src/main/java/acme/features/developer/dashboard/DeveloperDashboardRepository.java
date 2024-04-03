
package acme.features.developer.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface DeveloperDashboardRepository extends AbstractRepository {

	@Query("select count(t) from TrainingModule t where t.updateMoment IS NOT NULL and t.developer.id = :id")
	Integer totalNumberOfTrainingModulesWithUpdateMoment(int id);

	@Query("select count(t) from TrainingSession t where t.link IS NOT NULL and t.trainingModule.developer.id = :id")
	Integer totalNumberOfTrainingSessionsWithLink(int id);

	@Query("select avg(t.totalTime) from TrainingModule t where t.developer.id = :id")
	Double averageTotalTimeOfTrainingModulesPerDeveloper(int id);

	@Query("select avg(t.totalTime) from TrainingModule t where t.developer.id = :id")
	Double deviationTotalTimeOfTrainingModulesPerDeveloper(int id);

	@Query("select max(t.totalTime) from TrainingModule t where t.developer.id = :id")
	Double maxTotalTimeOfTrainingModulesPerDeveloper(int id);

	@Query("select min(t.totalTime) from TrainingModule t where t.developer.id = :id")
	Double minTotalTimeOfTrainingModulesPerDeveloper(int id);
}

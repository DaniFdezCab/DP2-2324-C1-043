
package acme.features.developer.trainingSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.trainings.TrainingModule;
import acme.entities.trainings.TrainingSession;

@Repository
public interface DeveloperTrainingSessionRepository extends AbstractRepository {

	@Query("select t from TrainingModule t where t.id = :id")
	TrainingModule findOneTrainingModuleById(int id);

	@Query("select t from TrainingSession t where t.trainingModule.id = :masterId")
	Collection<TrainingSession> findManyTrainingSessionsByMasterId(int masterId);

	@Query("select t.trainingModule from TrainingSession t where t.id = :id")
	TrainingModule findOneTrainingModuleByTrainingSessionId(int id);

	@Query("select t from TrainingSession t where t.id = :id")
	TrainingSession findOneTrainingSessionById(int id);

	@Query("select t from TrainingSession t where t.code = :code")
	TrainingSession findOneTrainingSessionByCode(String code);
}

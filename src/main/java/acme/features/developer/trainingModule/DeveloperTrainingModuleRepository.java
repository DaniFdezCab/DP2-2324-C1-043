
package acme.features.developer.trainingModule;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.trainings.TrainingModule;
import acme.entities.trainings.TrainingSession;
import acme.roles.Developer;

@Repository
public interface DeveloperTrainingModuleRepository extends AbstractRepository {

	@Query("select t from TrainingModule t where t.id = :id")
	TrainingModule findOneTrainingModuleById(int id);

	@Query("select t from TrainingModule t")
	Collection<TrainingModule> findAllTrainingModules();

	@Query("select d from Developer d WHERE d.id = :id")
	Developer findOneDeveloperById(int id);

	@Query("select t from TrainingModule t where t.developer.id = :developerId")
	Collection<TrainingModule> findManyTrainingModulesByDeveloperId(int developerId);

	@Query("select t from TrainingModule t where t.code = :code")
	TrainingModule findOneTrainingModuleByCode(String code);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select p from Project p where p.id = :projectId")
	Project findOneProjectById(int projectId);

	@Query("select t from TrainingSession t where t.trainingModule.id = :trainingModuleId")
	Collection<TrainingSession> findManyTrainingSessionsByTrainingModuleId(int trainingModuleId);

}

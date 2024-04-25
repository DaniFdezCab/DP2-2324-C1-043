
package acme.features.any.trainingModule;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.trainings.TrainingModule;

@Service
public class AnyTrainingModuleListService extends AbstractService<Any, TrainingModule> {

	@Autowired
	private AnyTrainingModuleRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<TrainingModule> objects;

		objects = this.repository.findManyPublishedTrainingModules();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		Dataset dataset;
		String payload;

		dataset = super.unbind(object, "code", "totalTime", "link");
		payload = String.format(//
			"%s; %s; %s; %s", //
			object.getDetails(), //
			object.getDeveloper().getIdentity().getFullName(), //
			object.getDeveloper().getDegree(), //
			object.getDeveloper().getSpecialisation());
		dataset.put("payload", payload);

		super.getResponse().addData(dataset);
	}
}


package acme.features.client.progressLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contracts.Contract;
import acme.entities.contracts.ProgressLog;
import acme.roles.Client;

@Service
public class ClientProgressLogCreateService extends AbstractService<Client, ProgressLog> {

	@Autowired
	private ClientProgressLogRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Contract contract;

		masterId = super.getRequest().getData("masterId", int.class);
		contract = this.repository.findOneContractById(masterId);

		Client c = contract.getClient();

		int activeClientId = super.getRequest().getPrincipal().getActiveRoleId();
		Client activeClient = this.repository.findOneClientById(activeClientId);
		boolean clientOwnsContract = c == activeClient;

		status = clientOwnsContract && contract != null && !contract.isDraftMode() && super.getRequest().getPrincipal().hasRole(contract.getClient());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProgressLog object;
		int masterId;
		Contract contract;

		masterId = super.getRequest().getData("masterId", int.class);
		contract = this.repository.findOneContractById(masterId);

		object = new ProgressLog();
		object.setDraftMode(true);
		object.setContract(contract);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final ProgressLog object) {

		assert object != null;

		super.bind(object, "recordId", "completeness", "comment", "registrationMoment", "responsiblePerson");
	}

	@Override
	public void validate(final ProgressLog object) {

		assert object != null;
	}

	@Override
	public void perform(final ProgressLog object) {

		assert object != null;

		object.setDraftMode(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final ProgressLog object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "recordId", "completeness", "comment", "registrationMoment", "responsiblePerson", "draftMode");

		dataset.put("masterId", object.getContract().getId());
		dataset.put("draftMode", object.isDraftMode());

		super.getResponse().addData(dataset);

	}

}

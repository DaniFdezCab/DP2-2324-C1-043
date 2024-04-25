
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contracts.Contract;
import acme.entities.projects.Project;
import acme.roles.Client;

@Service
public class ClientContractCreateService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientContractRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Contract contract = new Contract();
		Client client;

		client = this.repository.findOneClientById(super.getRequest().getPrincipal().getActiveRoleId());

		contract.setDraftMode(true);
		contract.setClient(client);

		super.getBuffer().addData(contract);

	}

	@Override
	public void bind(final Contract object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		object.setDraftMode(true);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget");
		object.setProject(project);

	}

	@Override
	public void validate(final Contract object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing;

			existing = this.repository.findOneContractByCode(object.getCode());
			super.state(existing == null, "code", "client.contract.form.error.duplicated");
		}

	}

	@Override
	public void perform(final Contract object) {

		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Contract object) {

		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;

		projects = this.repository.findAllProjects();
		choices = SelectChoices.from(projects, "code", object.getProject());

		Dataset dataset;

		dataset = super.unbind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}

}

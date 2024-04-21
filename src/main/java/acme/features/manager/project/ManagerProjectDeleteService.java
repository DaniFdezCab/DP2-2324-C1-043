
package acme.features.manager.project;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.AbstractEntity;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contracts.Contract;
import acme.entities.contracts.ProgressLogs;
import acme.entities.projects.AssociationProject;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Manager;

@Service
public class ManagerProjectDeleteService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int projectId;
		Project project;

		projectId = super.getRequest().getData("id", int.class);
		project = this.repository.findOneProjectById(projectId);
		status = project != null && !project.isPublished() && super.getRequest().getPrincipal().hasRole(project.getManager());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;

		super.bind(object, "code", "title", "summary", "cost", "url", "fatalErrors");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;
	}

	@Override
	public void perform(final Project object) {
		assert object != null;
		List<Contract> contracts;
		List<ProgressLogs> progressLogs;
		List<Sponsorship> sponsorships;
		List<Invoice> invoices;
		List<AssociationProject> associationProjects;
		int id = object.getId();

		contracts = (List<Contract>) this.repository.findManyContractsByProjectId(id);
		Set<Integer> contractIds = contracts.stream().map(AbstractEntity::getId).collect(Collectors.toSet());
		progressLogs = (List<ProgressLogs>) this.repository.findManyProgressLogsByContractIds(contractIds);

		sponsorships = (List<Sponsorship>) this.repository.findManySponsorshipsByProjectId(id);
		Set<Integer> sponsorShipIds = sponsorships.stream().map(AbstractEntity::getId).collect(Collectors.toSet());
		invoices = (List<Invoice>) this.repository.findManyInvoicesBySponsorshipIds(sponsorShipIds);

		associationProjects = (List<AssociationProject>) this.repository.findManyAssociationProjectByProjectId(id);

		this.repository.deleteAll(associationProjects);

		this.repository.deleteAll(invoices);
		this.repository.deleteAll(sponsorships);

		this.repository.deleteAll(progressLogs);
		this.repository.deleteAll(contracts);

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "summary", "cost", "url", "fatalErrors");

		super.getResponse().addData(dataset);
	}

}

/*
 * AdministratorUserAccountListService.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.auditor.codeAudit;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audits.CodeAudit;
import acme.entities.audits.Mark;
import acme.entities.audits.Type;
import acme.entities.projects.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditsCreateService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditsRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getPrincipal().getActiveRoleId();

		CodeAudit object = new CodeAudit();
		object.setAuditor(this.repository.findAuditorById(id));
		object.setPublished(false);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		int projectId = super.getRequest().getData("project", int.class);
		Project project = this.repository.findOneProjectById(projectId);
		object.setProject(project);
		super.bind(object, "code", "executionDate", "type", "proposedCorrectiveActions", "optionalLink", "project");
	}
	@Override
	public void validate(final CodeAudit object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			CodeAudit existing;

			existing = this.repository.findOneCodeAuditByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "auditor.codeaudit.form.error.duplicated");
		}
	}
	@Override
	public void perform(final CodeAudit object) {
		assert object != null;

		this.repository.save(object);
	}
	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		SelectChoices choices;
		SelectChoices choices2;
		Dataset dataset;
		List<Mark> marks;

		marks = this.repository.findMarksByCodeAuditId(object.getId()).stream().toList();

		Collection<Project> allProjects = this.repository.findAllProjects();
		choices = SelectChoices.from(Type.class, object.getType());
		choices2 = SelectChoices.from(allProjects, "code", (Project) allProjects.toArray()[0]);
		dataset = super.unbind(object, "code", "executionDate", "type", "proposedCorrectiveActions", "optionalLink", "project", "published");
		dataset.put("mark", this.repository.averageMark(marks));
		dataset.put("type", choices);
		dataset.put("projects", choices2);

		super.getResponse().addData(dataset);
	}
}

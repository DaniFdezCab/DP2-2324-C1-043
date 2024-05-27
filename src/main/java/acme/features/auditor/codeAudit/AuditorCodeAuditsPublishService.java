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
public class AuditorCodeAuditsPublishService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditsRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int codeAuditId;
		CodeAudit codeAudit;
		int auditor;

		codeAuditId = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findOneCodeAuditById(codeAuditId);
		auditor = super.getRequest().getPrincipal().getActiveRoleId();
		status = auditor == codeAudit.getAuditor().getId() && !codeAudit.isPublished();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CodeAudit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCodeAuditById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		super.bind(object, "code", "executionDate", "type", "proposedCorrectiveActions", "optionalLink", "project");
	}

	@Override
	public void validate(final CodeAudit object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("mark")) {
			List<Mark> marks;

			marks = this.repository.findMarksByCodeAuditId(object.getId()).stream().toList();
			Mark nota = this.repository.averageMark(marks);

			super.state(nota == Mark.C || nota == Mark.B || nota == Mark.A || nota == Mark.A_PLUS, "mark", "auditor.codeaudit.form.error.mark");
		}
	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;
		object.setPublished(!object.isPublished());

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
		dataset = super.unbind(object, "code", "executionDate", "type", "proposedCorrectiveActions", "optionalLink", "published");
		dataset.put("mark", this.repository.averageMark(marks));
		dataset.put("type", choices);
		dataset.put("projects", choices2);

		super.getResponse().addData(dataset);

	}

}

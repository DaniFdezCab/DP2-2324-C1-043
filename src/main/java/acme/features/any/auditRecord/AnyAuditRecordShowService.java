
package acme.features.any.auditRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audits.AuditRecord;
import acme.entities.audits.CodeAudit;
import acme.entities.audits.Mark;

@Service
public class AnyAuditRecordShowService extends AbstractService<Any, AuditRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyAuditRecordRepository repository;

	// AbstractService<Any, AuditRecord> ---------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		AuditRecord auditRecord;

		id = super.getRequest().getData("id", int.class);
		auditRecord = this.repository.findOneAuditRecordById(id);

		status = auditRecord != null && auditRecord.isPublished();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditRecord object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditRecordById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset;
		SelectChoices codeAudits;
		SelectChoices choices;

		Collection<CodeAudit> unpublishedCodeAudits = this.repository.findAllCodeAudits();
		codeAudits = SelectChoices.from(unpublishedCodeAudits, "code", object.getCodeAudit());
		choices = SelectChoices.from(Mark.class, object.getMark());

		dataset = super.unbind(object, "code", "furtherInformation", "mark", "published", "auditPeriodStart", "auditPeriodEnd");
		dataset.put("codeAudit", codeAudits.getSelected().getKey());
		dataset.put("codeaudits", codeAudits);
		dataset.put("marks", choices);

		super.getResponse().addData(dataset);
	}

}


package acme.features.auditor.auditRecord;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audits.AuditRecord;
import acme.entities.audits.CodeAudit;
import acme.entities.audits.Mark;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordCreateService extends AbstractService<Auditor, AuditRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditRecordRepository repository;

	// AbstractService<Auditor, AuditRecord> ---------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		AuditRecord object;

		object = new AuditRecord();
		object.setPublished(true);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final AuditRecord object) {
		assert object != null;

		int codeAuditId;
		CodeAudit codeAudit;

		codeAuditId = super.getRequest().getData("codeAudit", int.class);
		codeAudit = this.repository.findOneCodeAuditById(codeAuditId);
		object.setCodeAudit(codeAudit);
		super.bind(object, "code", "furtherInformation", "mark", "auditPeriodStart", "auditPeriodEnd");
	}

	@Override
	public void validate(final AuditRecord object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			AuditRecord isCodeUnique;
			isCodeUnique = this.repository.findAuditRecordByCode(object.getCode());
			super.state(isCodeUnique == null, "code", "auditor.codeaudit.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("auditPeriodEnd")) {
			Date auditPeriodStart;
			Date auditPeriodEnd;

			auditPeriodStart = object.getAuditPeriodStart();
			auditPeriodEnd = object.getAuditPeriodEnd();

			super.state(MomentHelper.isLongEnough(auditPeriodStart, auditPeriodEnd, 1, ChronoUnit.HOURS) && auditPeriodEnd.after(auditPeriodStart), "auditEndTime", "validation.auditrecord.error.oneHour");
		}
	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;

		object.setPublished(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset;
		SelectChoices codeAudits;
		SelectChoices choices;

		Collection<CodeAudit> allCodeAudits = this.repository.findAllCodeAudits();
		codeAudits = SelectChoices.from(allCodeAudits, "code", object.getCodeAudit());
		choices = SelectChoices.from(Mark.class, object.getMark());

		dataset = super.unbind(object, "code", "published", "furtherInformation", "mark", "auditPeriodStart", "auditPeriodEnd");
		dataset.put("codeAudit", codeAudits.getSelected().getKey());
		dataset.put("codeaudits", codeAudits);
		dataset.put("marks", choices);

		super.getResponse().addData(dataset);
	}

}

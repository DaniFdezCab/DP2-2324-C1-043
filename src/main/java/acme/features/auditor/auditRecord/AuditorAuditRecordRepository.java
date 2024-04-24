
package acme.features.auditor.auditRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.AuditRecord;
import acme.entities.audits.CodeAudit;

@Repository
public interface AuditorAuditRecordRepository extends AbstractRepository {

	@Query("select c from CodeAudit c where c.id = :codeAuditId")
	CodeAudit findOneCodeAuditById(int codeAuditId);

	@Query("select a from AuditRecord a where a.codeAudit.auditor.id = :auditorId")
	Collection<AuditRecord> findAuditRecordsByAuditorId(int auditorId);

	@Query("select a from AuditRecord a where a.codeAudit.id = :codeAuditId")
	Collection<AuditRecord> findAuditRecordsByCodeAuditId(int codeAuditId);

	@Query("select c from CodeAudit c")
	Collection<CodeAudit> findAllCodeAudits();

	@Query("select a from AuditRecord a where a.id = :auditRecordId")
	AuditRecord findOneAuditRecordById(int auditRecordId);

	@Query("select a from AuditRecord a where a.code = :code")
	AuditRecord findAuditRecordByCode(String code);

}


package acme.features.auditor.auditorDashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("select count(c) from CodeAudit c where c.type = acme.entities.audits.Type.STATIC and c.auditor.id = :auditorId")
	Integer auditsDynamic(int auditorId);

	@Query("select count(c) from CodeAudit c where c.type = acme.entities.audits.Type.DYNAMIC and c.auditor.id = :auditorId")
	Integer auditsStatic(int auditorId);

	@Query("select (select count(ar) from AuditRecord ar where ar.codeAudit.id = a.id) from CodeAudit a where a.auditor.id = :auditorId")
	Collection<Double> auditingRecordsPerAudit(int auditorId);

	@Query("select avg(select count(ar) from AuditRecord ar where ar.codeAudit.id = a.id) from CodeAudit a where a.auditor.id = :auditorId")
	Double averageAuditingRecords(int auditorId);

	@Query("select max(select count(ar) from AuditRecord ar where ar.codeAudit.id = a.id) from CodeAudit a where a.auditor.id = :auditorId")
	Integer maxAuditingRecords(int auditorId);

	@Query("select min(select count(ar) from AuditRecord ar where ar.codeAudit.id = a.id) from CodeAudit a where a.auditor.id = :auditorId")
	Integer minAuditingRecords(int auditorId);

	@Query("select avg(time_to_sec(timediff(ar.auditPeriodEnd, ar.auditPeriodStart)) / 3600) from AuditRecord ar where ar.codeAudit.auditor.id = :auditorId")
	Double averageRecordPeriod(int auditorId);

	@Query("select stddev(time_to_sec(timediff(ar.auditPeriodEnd, ar.auditPeriodStart)) / 3600) from AuditRecord ar where ar.codeAudit.auditor.id = :auditorId")
	Double deviationRecordPeriod(int auditorId);

	@Query("select min(time_to_sec(timediff(ar.auditPeriodEnd, ar.auditPeriodStart)) / 3600) from AuditRecord ar where ar.codeAudit.auditor.id = :auditorId")
	Double minimumRecordPeriod(int auditorId);

	@Query("select max(time_to_sec(timediff(ar.auditPeriodEnd, ar.auditPeriodStart)) / 3600) from AuditRecord ar where ar.codeAudit.auditor.id = :auditorId")
	Double maximumRecordPeriod(int auditorId);

}

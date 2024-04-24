/*
 * AdministratorUserAccountRepository.java
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

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.AuditRecord;
import acme.entities.audits.CodeAudit;
import acme.entities.audits.Mark;
import acme.entities.projects.Project;
import acme.roles.Auditor;

@Repository
public interface AuditorCodeAuditsRepository extends AbstractRepository {

	@Query("select c from CodeAudit c where c.id = :id")
	CodeAudit findOneCodeAuditById(int id);

	@Query("select c from CodeAudit c where c.auditor.id = :id")
	Collection<CodeAudit> findCodeAuditByAuditorId(int id);

	@Query("select c from CodeAudit c where c.auditor.id = :id and c.published = true")
	Collection<CodeAudit> findPublishedCodeAuditByAuditorId(int id);

	@Query("select c from CodeAudit c where c.code = :code")
	CodeAudit findOneCodeAuditByCode(String code);

	@Query("select a from AuditRecord a where a.codeAudit.id = :codeAuditId")
	Collection<AuditRecord> findAuditRecordsByCodeAuditId(int codeAuditId);

	@Query("select a from Auditor a  where a.id = :auditorId")
	Auditor findAuditorById(int auditorId);

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select a.mark from AuditRecord a where a.codeAudit.id = :auditId")
	Collection<Mark> findMarksByAuditId(int auditId);

	@Query("select c from CodeAudit c where c.code = :code and c.id != :auditId")
	CodeAudit findCodeAuditByCodeDifferentId(String code, int auditId);

	@Query("select ad.mark from AuditRecord ad where ad.codeAudit.id=:codeAuditId")
	Collection<Mark> findMarksByCodeAuditId(int codeAuditId);

	default int changeMarkToNumber(final Mark mark) {
		int res = 0;

		if (mark == Mark.F_MINOR)
			res = 0;
		else if (mark == Mark.F)
			res = 1;
		else if (mark == Mark.C)
			res = 2;
		else if (mark == Mark.B)
			res = 3;
		else if (mark == Mark.A)
			res = 4;
		else
			res = 5;

		return res;
	}

	default Mark changeNumberToMark(final double media) {
		Mark res = Mark.C;
		double average = Math.round(media);
		if (average < 1)
			res = Mark.F_MINOR;
		else if (average < 2 && average >= 1)
			res = Mark.F;
		else if (average < 3 && average >= 2)
			res = Mark.C;
		else if (average < 4 && average >= 3)
			res = Mark.B;
		else if (average < 5 && average >= 4)
			res = Mark.A;
		else
			res = Mark.A_PLUS;
		return res;
	}

	default Mark averageMark(final List<Mark> ls) {
		double average = ls.stream().mapToDouble(m -> this.changeMarkToNumber(m)).average().orElse(0);
		Mark res = this.changeNumberToMark(average);
		return res;
	}
}


package acme.features.any.codeAudit;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.CodeAudit;
import acme.entities.audits.Mark;
import acme.entities.projects.Project;

@Repository
public interface AnyCodeAuditRepository extends AbstractRepository {

	@Query("select c from CodeAudit c where c.id = :id")
	CodeAudit findOneCodeAuditById(int id);

	@Query("select c from CodeAudit c where c.published = true")
	Collection<CodeAudit> findAllPublishedCodeAudits();

	@Query("select ad.mark from AuditRecord ad where ad.codeAudit.id=:codeAuditId")
	Collection<Mark> findMarksByCodeAuditId(int codeAuditId);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

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

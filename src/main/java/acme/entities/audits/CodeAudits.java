
package acme.entities.audits;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CodeAudits extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	// Attributes
	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	@Column(unique = true)
	private String				code;

	@Past
	private Date				executionDate;

	private String				type;

	@Size(max = 101)
	private String				proposedCorrectiveActions;// Lista da error

	private int					mark;


	public void computeMark(final List<CodeAudits> auditingRecords) {
		if (auditingRecords.isEmpty())
			this.mark = 0;
		return;
	}


	@OneToMany(mappedBy = "codeAudits")
	private List<AuditRecords>	auditRecords;

	@URL
	private String				optionalLink;

	// Constructors, getters, setters, hashCode, equals, etc.


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(this.code, this.executionDate, this.optionalLink, this.mark, this.proposedCorrectiveActions, this.type);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		CodeAudits other = (CodeAudits) obj;
		return Objects.equals(this.code, other.code) && Objects.equals(this.executionDate, other.executionDate) && Objects.equals(this.optionalLink, other.optionalLink) && this.mark == other.mark
			&& Objects.equals(this.proposedCorrectiveActions, other.proposedCorrectiveActions) && Objects.equals(this.type, other.type);
	}
}

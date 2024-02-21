
package acme.entities.audits;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditRecords extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	// Attributes
	@NotBlank
	@Pattern(regexp = "AU-[0-9]{4}-[0-9]{3}")
	@Column(unique = true)
	private String				code;

	@Past
	private Date				auditPeriodStart;

	@Past
	private Date				auditPeriodEnd;// al menos una hora ?

	@NotBlank
	@Pattern(regexp = "^(A\\+|A|B|C|F|F-)$")// record?
	private String				mark;

	@ManyToOne
	private CodeAudits			codeAudits;

	@URL
	private String				furtherInformation;


	// Constructors, getters, setters, hashCode, equals, etc.
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(this.auditPeriodEnd, this.auditPeriodStart, this.code, this.codeAudits, this.furtherInformation, this.mark);
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
		AuditRecords other = (AuditRecords) obj;
		return Objects.equals(this.auditPeriodEnd, other.auditPeriodEnd) && Objects.equals(this.auditPeriodStart, other.auditPeriodStart) && Objects.equals(this.code, other.code) && Objects.equals(this.codeAudits, other.codeAudits)
			&& Objects.equals(this.furtherInformation, other.furtherInformation) && Objects.equals(this.mark, other.mark);
	}
}

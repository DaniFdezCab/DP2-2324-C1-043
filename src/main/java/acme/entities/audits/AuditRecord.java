
package acme.entities.audits;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditRecord extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	// Attributes
	@NotBlank
	@Pattern(regexp = "AU-[0-9]{4}-[0-9]{3}")
	@Column(unique = true)
	private String				code;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				auditPeriodStart;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				auditPeriodEnd;


	public boolean haPasadoAlMenosUnaHora() {

		// Calcular la diferencia en milisegundos
		long diferencia = this.auditPeriodEnd.getTime() - this.auditPeriodStart.getTime();

		// Convertir la diferencia a horas
		long horas = diferencia / (1000 * 60 * 60);

		// Devolver true si han pasado al menos una hora
		return horas >= 1;
	}


	@NotNull
	private Mark		mark;

	@ManyToOne
	private CodeAudit	codeAudit;

	@URL
	@Length(max = 255)
	private String		furtherInformation;


	// Constructors, getters, setters, hashCode, equals, etc.
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(this.auditPeriodEnd, this.auditPeriodStart, this.code, this.codeAudit, this.furtherInformation, this.mark);
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
		AuditRecord other = (AuditRecord) obj;
		return Objects.equals(this.auditPeriodEnd, other.auditPeriodEnd) && Objects.equals(this.auditPeriodStart, other.auditPeriodStart) && Objects.equals(this.code, other.code) && Objects.equals(this.codeAudit, other.codeAudit)
			&& Objects.equals(this.furtherInformation, other.furtherInformation) && Objects.equals(this.mark, other.mark);
	}
}

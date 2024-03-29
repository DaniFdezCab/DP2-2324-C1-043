
package acme.entities.audits;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class CodeAudit extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	// Attributes
	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	@Column(unique = true)
	private String				code;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				executionDate;

	private Type				type;

	@Length(max = 100)
	private String				proposedCorrectiveActions;

	@NotNull
	private Mark				mark;


	public static Mark moda(final List<Mark> lista) {
		// Crear un mapa para almacenar las frecuencias de cada valor
		Map<Mark, Integer> frecuencias = new HashMap<>();
		for (Mark valor : lista)
			frecuencias.put(valor, frecuencias.getOrDefault(valor, 0) + 1);

		// Encontrar el valor con la mayor frecuencia
		Mark moda = null;
		int frecuenciaMaxima = 0;
		for (Map.Entry<Mark, Integer> entrada : frecuencias.entrySet())
			if (entrada.getValue() > frecuenciaMaxima) {
				frecuenciaMaxima = entrada.getValue();
				moda = entrada.getKey();
			}

		// Devolver la moda
		return moda;
	}


	@URL
	@Length(max = 255)
	private String optionalLink;

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
		CodeAudit other = (CodeAudit) obj;
		return Objects.equals(this.code, other.code) && Objects.equals(this.executionDate, other.executionDate) && Objects.equals(this.optionalLink, other.optionalLink) && this.mark == other.mark
			&& Objects.equals(this.proposedCorrectiveActions, other.proposedCorrectiveActions) && Objects.equals(this.type, other.type);
	}
}


package acme.entities;

import java.sql.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsorship extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	//Attributes --------------------------

	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	private String				code;

	@Past
	private Date				moment;

	@Future
	@Min(value = 1)
	private Integer				duration;

	@Positive
	private double				amount;

	@NotBlank
	@Pattern(regexp = "Financial|In kind")
	private String				type;

	@Email
	private String				optionalContact;

	@URL
	private String				optionalLink;


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(this.amount, this.code, this.duration, this.moment, this.optionalContact, this.optionalLink, this.type);
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
		Sponsorship other = (Sponsorship) obj;
		return Double.doubleToLongBits(this.amount) == Double.doubleToLongBits(other.amount) && Objects.equals(this.code, other.code) && Objects.equals(this.duration, other.duration) && Objects.equals(this.moment, other.moment)
			&& Objects.equals(this.optionalContact, other.optionalContact) && Objects.equals(this.optionalLink, other.optionalLink) && Objects.equals(this.type, other.type);
	}

}

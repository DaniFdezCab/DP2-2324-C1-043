
package acme.entities.claims;

import java.sql.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Claim extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Pattern(regexp = "C-[0-9]{4}")
	@Column(unique = true)
	private String				code;

	@Past
	private Date				instantiationMoment;

	@NotBlank
	@Length(max = 76)
	private String				heading;

	@NotBlank
	@Length(max = 101)
	private String				description;

	@NotBlank
	@Length(max = 101)
	private String				department;

	@Email
	private String				optionalEmailAddress;

	@URL
	private String				optionalLink;


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(this.code, this.department, this.description, this.heading, this.instantiationMoment, this.optionalEmailAddress, this.optionalLink);
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
		Claim other = (Claim) obj;
		return Objects.equals(this.code, other.code) && Objects.equals(this.department, other.department) && Objects.equals(this.description, other.description) && Objects.equals(this.heading, other.heading)
			&& Objects.equals(this.instantiationMoment, other.instantiationMoment) && Objects.equals(this.optionalEmailAddress, other.optionalEmailAddress) && Objects.equals(this.optionalLink, other.optionalLink);
	}

}

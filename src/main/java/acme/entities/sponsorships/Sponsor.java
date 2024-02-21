
package acme.entities.sponsorships;

import java.util.Objects;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsor extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 76)
	private String				name;

	@NotBlank
	@Length(max = 101)
	private String				benefits;

	@URL
	private String				optionalWebPage;

	@Email
	private String				optionalEmailContact;


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(this.benefits, this.name, this.optionalEmailContact, this.optionalWebPage);
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
		Sponsor other = (Sponsor) obj;
		return Objects.equals(this.benefits, other.benefits) && Objects.equals(this.name, other.name) && Objects.equals(this.optionalEmailContact, other.optionalEmailContact) && Objects.equals(this.optionalWebPage, other.optionalWebPage);
	}

}

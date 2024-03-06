
package acme.entities.sponsorships;

import java.sql.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Invoice extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	// Attributes
	@NotBlank
	@Pattern(regexp = "IN-[0-9]{4}-[0-9]{4}")
	@Column(unique = true)
	private String				code;

	@Past
	private Date				registrationTime;

	@Future
	@Min(value = 31)
	private Date				dueDate;

	@Positive
	private Integer				quantity;

	@PositiveOrZero
	private Double				tax;


	private Double getTotalAmount() {
		return this.quantity + this.tax;
	}


	@URL
	private String optionalLink;


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(this.code, this.dueDate, this.optionalLink, this.quantity, this.registrationTime, this.tax);
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
		Invoice other = (Invoice) obj;
		return Objects.equals(this.code, other.code) && Objects.equals(this.dueDate, other.dueDate) && Objects.equals(this.optionalLink, other.optionalLink) && Double.doubleToLongBits(this.quantity) == Double.doubleToLongBits(other.quantity)
			&& Objects.equals(this.registrationTime, other.registrationTime) && Double.doubleToLongBits(this.tax) == Double.doubleToLongBits(other.tax);
	}

}

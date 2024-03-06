
package acme.entities.sponsorships;

import java.beans.Transient;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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

	// Relationships ----------------------------------------------------------

	@ManyToOne()
	private Sponsorship			sponsorship;

	// Attributes
	@NotBlank
	@Pattern(regexp = "IN-[0-9]{4}-[0-9]{4}")
	@Column(unique = true)
	private String				code;

	@Past
	private Date				registrationTime;

	//Aún no hemos visto como validar un @Future de forma correcta
	private Date				dueDate;

	@Positive
	private Integer				quantity;

	@PositiveOrZero
	private Double				tax;

	// Derived attributes -----------------------------------------------------


	@Transient
	private Double getTotalAmount() {
		return this.quantity + this.tax;
	}


	@URL
	private String moreInfo;

}


package acme.entities.sponsorships;

import java.beans.Transient;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Invoice extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	// Relationships ----------------------------------------------------------

	@ManyToOne(optional = false)
	private Sponsorship			sponsorship;

	// Attributes
	@NotBlank
	@Pattern(regexp = "IN-[0-9]{4}-[0-9]{4}")
	@Column(unique = true)
	@NotNull
	private String				code;

	@Past
	@NotNull
	private Date				registrationTime;

	@NotNull
	private Date				dueDate;

	@NotNull
	private Money				quantity;

	@PositiveOrZero
	private Double				tax;

	@URL
	private String				moreInfo;

	// Derived attributes -----------------------------------------------------


	@Transient
	private Money getTotalAmount() {
		Money money = null;
		Double res = this.getQuantity().getAmount() * (1. + this.tax);
		money.setAmount(res);
		money.setCurrency(this.quantity.getCurrency());
		return money;
	}

}


package acme.entities.sponsorships;

import java.beans.Transient;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
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

	@Valid
	@NotNull
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
	@Temporal(TemporalType.TIMESTAMP)
	private Date				registrationTime;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				dueDate;

	@NotNull
	private Money				quantity;

	@DecimalMin(value = "0.00")
	@DecimalMax(value = "1.00")
	private Double				tax;

	@URL
	@Length(max = 255)
	private String				moreInfo;

	private boolean				draftMode;

	// Derived attributes -----------------------------------------------------


	@Transient
	public Money totalAmount() {
		Money money = new Money();
		Double res = this.getQuantity().getAmount() * (1. + this.tax);
		money.setAmount(res);
		money.setCurrency(this.quantity.getCurrency());
		return money;
	}

}

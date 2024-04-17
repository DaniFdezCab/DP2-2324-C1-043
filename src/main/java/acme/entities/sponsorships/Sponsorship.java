
package acme.entities.sponsorships;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.projects.Project;
import acme.roles.Sponsor;
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

	// Relationships ----------------------------------------------------------

	@ManyToOne(optional = false)
	private Project				project;

	@ManyToOne(optional = false)
	private Sponsor				sponsor;

	//Attributes --------------------------

	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	@Column(unique = true)
	private String				code;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				moment;

	@Min(value = 1)
	private float				duration;

	@Positive
	private Integer				amount;

	@NotBlank
	@Pattern(regexp = "Financial|In kind")
	private String				type;

	@Email
	@Length(max = 255)
	private String				emailContact;

	@URL
	@Length(max = 255)
	private String				moreInfo;

	@NotNull
	public Boolean				published			= false;

}

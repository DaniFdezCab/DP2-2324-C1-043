
package acme.entities.sponsorships;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.projects.Project;
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

	@ManyToOne
	private Project				project;

	@OneToMany
	private List<Invoice>		invoice;

	//Attributes --------------------------

	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	@Column(unique = true)
	private String				code;

	@Past
	private Date				moment;

	@Future
	@Min(value = 31)
	private Integer				duration;

	@Positive
	private Integer				amount;

	@NotBlank
	@Pattern(regexp = "Financial|In kind")
	private String				type;

	@Email
	@Length(max = 255)
	private String				emailContact;

	@URL
	private String				optionalLink;

}

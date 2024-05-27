
package acme.entities.sponsorships;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.projects.Project;
import acme.roles.Sponsor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "id"), @Index(columnList = "sponsor_id"), @Index(columnList = "code"), @Index(columnList = "sponsor_id, draftMode")
})
public class Sponsorship extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	// Relationships ----------------------------------------------------------

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	private Project				project;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	@NotNull
	private Sponsor				sponsor;

	//Attributes --------------------------

	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	@Column(unique = true)
	@NotNull
	private String				code;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				moment;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				startDuration;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endDuration;

	@NotNull
	private Money				amount;

	@NotNull
	private SponsorshipType		type;

	@Email
	@Length(max = 255)
	private String				emailContact;

	@URL
	@Length(max = 255)
	private String				moreInfo;

	private boolean				draftMode;

}
